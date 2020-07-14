import akka.actor.ActorSystem
import akka.http.interop.HttpServer
import akka.http.scaladsl.server.Route
import api.Api
import com.typesafe.config.{Config, ConfigFactory}
import configs.AppConfig
import services.impl.TodoServiceImpl
import slick.interop.zio.DatabaseProvider
import subscribers.{Subscriber, TodoSub}
import zio._
import zio.config.typesafe.TypesafeConfig
import zio.console.{Console, putStrLn}

object MainServer extends App {

  def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    ZIO(ConfigFactory.load.resolve)
      .flatMap(rawConfig => program.provideCustomLayer(prepareEnvironment(rawConfig)))
      .as(ExitCode.success)
      .catchAll(error => putStrLn(error.getMessage).as(ExitCode.failure))

  private val program: ZIO[HttpServer with Console, Throwable, Unit] =
    HttpServer.start.tapM(_ => putStrLn(s"Server online.")).useForever

  private def prepareEnvironment(rawConfig: Config): TaskLayer[HttpServer with Subscriber] = {
    val configLayer    = TypesafeConfig.fromTypesafeConfig(rawConfig, AppConfig.descriptor)
    val dbConfigLayer  = ZLayer.fromEffect(ZIO(rawConfig.getConfig("db")))
    val dbBackendLayer = ZLayer.succeed(slick.jdbc.PostgresProfile.backend)
    val apiConfigLayer = configLayer.map(c => Has(c.get.api))

    val actorSystemLayer: TaskLayer[Has[ActorSystem]] = ZLayer.fromManaged {
      ZManaged.make(ZIO(ActorSystem("todozio-system")))(s =>
        ZIO.fromFuture(_ => s.terminate()).either
      )
    }

    val dbLayer = (dbConfigLayer ++ dbBackendLayer) >>> DatabaseProvider.live >>>
      TodoServiceImpl.live

    val apiLayer: TaskLayer[Api] = (apiConfigLayer ++ dbLayer) >>> Api.live

    val routesLayer: ZLayer[Api, Nothing, Has[Route]] =
      ZLayer.fromService(_.routes)

    (actorSystemLayer ++ apiConfigLayer ++ (apiLayer >>> routesLayer)) >>>  TodoSub.live ++ HttpServer.live
  }

}
