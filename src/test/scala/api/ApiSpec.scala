package api

import akka.http.interop.HttpServer
import routes.TodoRouteSpec
import services.TodoServiceMock
import zio.ZLayer
import zio.blocking.Blocking
import zio.clock.Clock
import zio.test.{DefaultRunnableSpec, _}

object ApiSpec extends DefaultRunnableSpec {

  private val env =
    (
      ZLayer.succeed(HttpServer.Config("localhost", 9000)) ++
        TodoServiceMock.test
    ) >>> Api.live.passthrough ++ Blocking.live ++ Clock.live

  private val apiSpecs = suite("Api")(
    TodoRouteSpec.specs
  )

  private val serviceSpecs = suite("Service")(
    TodoRouteSpec.specs
  )

  private val specs = suite("Tests")(apiSpecs, serviceSpecs)

  override def spec = specs.provideLayer(env)

}
