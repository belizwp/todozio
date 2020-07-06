package api

final case class ApiRoute(method: String, path: String) {

  def route: String = s"$method $path"

}
