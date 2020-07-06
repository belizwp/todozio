package services.impl

import models.Todo
import repositories.TodoRepository
import services.TodoService
import slick.interop.zio.DatabaseProvider
import slick.interop.zio.syntax._
import zio.{IO, ZIO, ZLayer}

final class TodoServiceImpl(env: DatabaseProvider) extends TodoService.Service {

  override def list(): IO[String, List[Todo]] = {
    ZIO
      .fromDBIO(TodoRepository.list())
      .mapError(_.getMessage)
      .map(_.toList)
  }.provide(env)

}

object TodoServiceImpl {

  val live: ZLayer[DatabaseProvider, Throwable, TodoService] =
    ZLayer.fromFunctionM { env =>
      ZIO.succeed(new TodoServiceImpl(env))
    }

}
