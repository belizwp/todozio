package routes

import akka.event.Logging.InfoLevel
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import facades.TodoFacade
import services.TodoService
import io.circe.syntax._
import io.circe.generic.auto._

class TodoRoute(env: TodoService) extends BaseRoute {

  val route: Route = pathPrefix("v1" / "todos") {
    logRequestResult("todos", InfoLevel) {
      (get & pathEndOrSingleSlash) {
        complete(TodoFacade.list().provide(env).map(_.asJson))
      }
    }
  }

}
