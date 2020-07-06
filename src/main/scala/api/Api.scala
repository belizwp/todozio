package api

import akka.http.interop._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import routes.{SystemRoute, TodoRoute}
import services.TodoService
import zio._
import zio.config.Config

object Api {

  trait Service {

    def routes: Route

  }

  val live: ZLayer[Config[HttpServer.Config] with TodoService, Nothing, Api] =
    ZLayer.fromFunction(env =>
      new Service with ZIOSupport {

        def routes: Route = systemRoute ~ todoRoute

        val systemRoute: Route = new SystemRoute().route

        val todoRoute: Route = new TodoRoute(env).route

      }
    )

  // accessors
  val routes: URIO[Api, Route] = ZIO.access[Api](a => Route.seal(a.get.routes))

}
