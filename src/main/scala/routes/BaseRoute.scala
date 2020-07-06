package routes

import akka.http.interop.ZIOSupport
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait BaseRoute extends ZIOSupport with FailFastCirceSupport
