package base

import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.testkit.{RouteTest, TestFrameworkInterface}

trait UnitSpec extends TestFrameworkInterface with RouteTest {

  def failTest(msg: String): Nothing = throw new Exception(msg)

  def testExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: Throwable => throw e
  }

}
