package configs

import akka.http.interop.HttpServer
import zio.config.magnolia
import zio.config.magnolia.DeriveConfigDescriptor

final case class AppConfig(api: HttpServer.Config)

object AppConfig {

  val descriptor: magnolia.DeriveConfigDescriptor.Descriptor[AppConfig] =
    DeriveConfigDescriptor.descriptor[AppConfig]

}
