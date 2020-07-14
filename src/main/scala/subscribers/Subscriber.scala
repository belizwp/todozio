package subscribers

import java.io.IOException

import com.typesafe.scalalogging.LazyLogging
import io.grpc.stub.StreamObserver
import io.kubemq.sdk.event.EventReceive
import io.kubemq.sdk.subscription.{SubscribeRequest, SubscribeType}
import io.kubemq.sdk.tools.Converter
import zio.IO

import scala.util.{Failure, Success, Try}

object Subscriber extends LazyLogging {

  trait Service {

    protected def process(message: String): IO[String, Unit]

    val channelName      = "testing_event_channel"

    val clientID         = "hello-world-subscriber"

    val kubeMQAddress    = "localhost:50000"

    val subscriber       = new io.kubemq.sdk.event.Subscriber(kubeMQAddress)

    val subscribeRequest = new SubscribeRequest()

    subscribeRequest.setChannel(channelName)
    subscribeRequest.setClientID(clientID)
    subscribeRequest.setSubscribeType(SubscribeType.Events)

    val streamObserver: StreamObserver[EventReceive] = new StreamObserver[EventReceive]() {

      override def onNext(value: EventReceive): Unit = Try {
        logger.info(
          "Event Received: EventID: {}, Channel: {}, Metadata: {}, Body: {}",
          value.getEventId,
          value.getChannel,
          value.getMetadata
        )

        Converter.FromByteArray(value.getBody).asInstanceOf[String]
      } match {
        case Failure(e: ClassNotFoundException) =>
          logger.error("ClassNotFoundException: {}", e.getMessage)
        case Failure(e: IOException)            => logger.error("IOException:  {}", e.getMessage)
        case Success(message)                   => message
      }

      override def onError(t: Throwable): Unit = {
        logger.error("Event Received Error: {}", t.getMessage)
        onCompleted()
      }

      override def onCompleted(): Unit = synchronized {
        notify()
      }

    }

    Try(subscribe(subscriber, subscribeRequest, streamObserver)) match {
      case Failure(exception) => logger.error("Start Subscriber Error {}", exception.getMessage)
      case Success(_)         => ()
    }

    private def subscribe(
        subscriber: io.kubemq.sdk.event.Subscriber,
        subscribeRequest: SubscribeRequest,
        streamObserver: StreamObserver[EventReceive]
    ): Unit = synchronized {
      subscriber.SubscribeToEvents(subscribeRequest, streamObserver)
      wait()
      subscribe(subscriber, subscribeRequest, streamObserver)
    }

  }

}
