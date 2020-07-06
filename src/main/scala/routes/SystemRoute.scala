package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class SystemRoute {

  val route: Route = (get & path("system" / "version")) {
    complete("hello system")
  }

}
