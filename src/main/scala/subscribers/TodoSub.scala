package subscribers

import services.TodoService
import zio.{IO, ZIO, ZLayer}

object TodoSub {

  val live: ZLayer[Any, Nothing, Subscriber] = ZLayer.fromFunctionM { env =>
    ZIO.succeed(new Subscriber.Service {
      override protected def process(message: String): IO[String, Unit] = ZIO.succeed(())
    })
  }

}
