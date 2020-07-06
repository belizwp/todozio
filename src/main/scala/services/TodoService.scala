package services

import models.Todo
import zio.IO

object TodoService {

  trait Service {

    def list(): IO[String, List[Todo]]

  }

}
