package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import repositories.DatabaseProfile
import services.TodoService

class TodoRoute(env: DatabaseProfile) extends BaseRoute {

  val route: Route = pathPrefix("v1" / "todos") {
    (get & pathEndOrSingleSlash) {
      complete(TodoService.list().provide(env).map(_.toString()))
    }
//
//    ~ (post & pathEndOrSingleSlash) {
//      complete("create todo")
//    } ~ (get & path(Segment)) { id =>
//      complete(s"get todo id: $id")
//    } ~ (post & path(Segment)) { id =>
//      complete(s"update todo id: $id")
//    } ~ (get & path(Segment / "title")) { id =>
//      complete(s"get title for todo id: $id")
//    }
  }

}
