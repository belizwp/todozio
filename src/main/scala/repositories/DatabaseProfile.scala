package repositories

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.{UIO, ZIO}

class DatabaseProfile {

  val dbConfig: UIO[DatabaseConfig[JdbcProfile]] = ZIO.effectTotal(DatabaseConfig.forConfig("pg"))

}
