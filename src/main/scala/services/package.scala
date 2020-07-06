import zio.Has

package object services {

  type TodoService = Has[TodoService.Service]

}
