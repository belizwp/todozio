package services

import models.Todo
import zio.{IO, Layer, ZIO, ZLayer}

class TodoServiceMock extends TodoService.Service {

  override def list(): IO[String, List[Todo]] = {
    ZIO.succeed(List.empty)
  }

}

object TodoServiceMock {

  val test: Layer[Nothing, TodoService] = ZLayer.succeed(new TodoServiceMock())

}
