package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import facades.TodoFacade
import io.circe.generic.auto._
import io.circe.syntax._
import services.TodoService

class TodoRoute(env: TodoService) extends BaseRoute {

  val route: Route = logResponseTime {
    pathPrefix("v1" / "todos") {
      (get & pathEndOrSingleSlash) {
        complete(TodoFacade.list().provide(env).map(_.asJson))
      }
    }
  }

}
