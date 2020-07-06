package interop.slick

import repositories.DatabaseProfile
import slick.dbio.DBIO
import zio.{Task, ZIO}

import scala.concurrent.ExecutionContext

object dbio {

//  implicit class ZIOObjOps(private val obj: ZIO.type) extends AnyVal {
//
//    def fromDBIO[R](dbio: DBIO[R]): ZIO[DatabaseProfile, Throwable, R] = {
//      for {
//        dbConfig <- ZIO.accessM[DatabaseProfile](_.dbConfig).map(x => ZIO.effectTotal(x))
//        r        <- zio(ec => dbConfig.db.run(dbio))
//      } yield r
//    }
//
//  }
//
//  private def zio[A](make: ExecutionContext => scala.concurrent.Future[A]): Task[A] =
//    ZIO.fromFuture(make)

}
