package services

import models.Todo
import repositories.{DatabaseProfile, TodoRepository}
import zio.ZIO

object TodoService {

  def list(): ZIO[DatabaseProfile, String, List[Todo]] = {
    (for {
      dbConfig <- ZIO.accessM[DatabaseProfile](_.dbConfig)
      r        <- ZIO.fromFuture(ec => dbConfig.db.run(TodoRepository.list()))
    } yield {
      r
    }).mapError(_.getMessage).map(_.toList)
  }

}
