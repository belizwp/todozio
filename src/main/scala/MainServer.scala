import akka.Done
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.Http
import com.typesafe.scalalogging.LazyLogging
import repositories.DatabaseProfile
import routes.TodoRoute

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object MainServer extends LazyLogging {

  implicit val system: ActorSystem = ActorSystem("todozio")

  implicit val ec: ExecutionContextExecutor = system.dispatcher

  class LiveEnv extends DatabaseProfile

  val env = new LiveEnv()

  def main(args: Array[String]): Unit = {
    val shut   = CoordinatedShutdown(system)
    val routes = new TodoRoute(env).route

    (for {
      bind <- Http().bindAndHandle(routes, "0.0.0.0", 9000)
    } yield (bind, shut)).foreach {
      case (binding, shutdown) =>
        logger.info("start server")

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceUnbind,
          "http-unbind"
        ) { () =>
          logger.info("unbind")
          binding.unbind().map(_ => Done)
        }

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceRequestsDone,
          "http-graceful-terminate"
        ) { () =>
          logger.info("terminate")
          binding.terminate(10.seconds).map(_ => Done)
        }

        shutdown.addTask(
          CoordinatedShutdown.PhaseServiceStop, "http-shutdown"
        ) { () =>
          logger.info("shutdown")
          Http().shutdownAllConnectionPools().map(_ => Done)
        }
    }
  }

}
