package repositories

import models.Todo
import slick.jdbc.PostgresProfile.api._

class TodoRepository {

  class TodoTable(tag: Tag) extends Table[Todo](tag, "todos") {

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey)

    def title: Rep[String] = column[String]("title")

    def * = (id, title) <> (Todo.tupled, Todo.unapply)

  }

  private val tableQuery = TableQuery[TodoTable]

  def list(): DBIO[Seq[Todo]] = tableQuery.result

}

object TodoRepository extends TodoRepository
