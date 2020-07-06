package routes

import interop.akka.ZioSupport
import zio.Runtime
import zio.internal.Platform

trait BaseRoute extends ZioSupport {

  override val environment: Unit = Runtime.default.environment

  override val platform: Platform = Runtime.default.platform

}
