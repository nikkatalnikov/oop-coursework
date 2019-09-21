package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._


trait Person {
  def id: Option[Long]
  def name: Option[String]
  def surname: Option[String]
}

import scala.concurrent.{ExecutionContext, Future}

class Persons @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  def updatePersonalInfo(person: Person, tableName: String): Future[String] = {
    val q = sqlu"update $tableName set name = ${person.name}, surname = ${person.surname} WHERE id = ${person.id.get}"
    dbConfig.db.run(q).map(_ => "Person successfully updated").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

}
