package routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.RouteTest
import api.{Api, ApiRoute}
import base.UnitSpec
import zio.blocking._
import zio.test.Assertion._
import zio.test._

object TodoRouteSpec extends UnitSpec {

  private val GET_TODO_LIST = ApiRoute("GET", "/v1/todos")

  val specs = Seq(
    testM(GET_TODO_LIST.route + " should return 200") {
      for {
        routes      <- Api.routes
        request      = Get(GET_TODO_LIST.path)
        resultCheck <- effectBlocking(request ~> routes ~> check {
                         val theStatus = status
                         val theCT     = contentType
                         assert(theStatus)(equalTo(StatusCodes.OK)) &&
                         assert(theCT)(equalTo(ContentTypes.`application/json`))
                       })
      } yield resultCheck
    },
  )

}
