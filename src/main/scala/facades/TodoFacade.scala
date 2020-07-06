package facades

import models.Todo
import services.TodoService
import zio.ZIO

object TodoFacade {

  def list(): ZIO[TodoService, String, List[Todo]] = {
    ZIO.accessM(_.get.list())
  }

}
