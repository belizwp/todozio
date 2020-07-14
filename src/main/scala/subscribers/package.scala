import zio.Has

package object subscribers {

  type Subscriber = Has[Subscriber.Service]

}
