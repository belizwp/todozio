package routes

import akka.event.Logging.LogLevel
import akka.event.{Logging, LoggingAdapter}
import akka.http.interop.ZIOSupport
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.RouteResult
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait BaseRoute extends ZIOSupport with FailFastCirceSupport {

  def akkaResponseTimeLoggingFunction(
      loggingAdapter: LoggingAdapter,
      requestTimestamp: Long,
      level: LogLevel = Logging.InfoLevel
  )(req: HttpRequest)(res: RouteResult): Unit = {
    val entry = res match {
      case Complete(resp)   =>
        val responseTimestamp: Long = System.nanoTime
        val elapsedTime: Long       = (responseTimestamp - requestTimestamp) / 1000000
        val userAgent               = req.headers.find(p => p.is("user-agent")).map(_.toString()).getOrElse("N/A")
        val loggingString           =
          s"""${req.method.value} ${req.uri} with ${resp.status} in $elapsedTime ms. $userAgent"""
        LogEntry(loggingString, level)
      case Rejected(reason) =>
        LogEntry(s"Rejected Reason: ${reason.mkString(",")}", level)
    }
    entry.logTo(loggingAdapter)
  }

  def printResponseTime(log: LoggingAdapter) = {
    val requestTimestamp = System.nanoTime
    akkaResponseTimeLoggingFunction(log, requestTimestamp) _
  }

  val logResponseTime = DebuggingDirectives.logRequestResult(LoggingMagnet(printResponseTime))

}
