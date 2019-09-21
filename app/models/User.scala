package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.{GetResult, JdbcProfile}

import scala.concurrent.{ExecutionContext, Future}


case class User(id: Option[Long], login: String, password: String, name: Option[String], surname: Option[String]) extends Person

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

import slick.jdbc.MySQLProfile.api._

class Users @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  def add(user: User): Future[String] = {
    val q = sqlu"insert into operator (login, password, name, surname) values (${user.login}, ${user.password}, ${user.name}, ${user.surname})"
    dbConfig.db.run(q).map(_ => "User successfully added").recover {
      case ex: Exception => ex.getMessage
    }
  }

  def checkPass(password: String): Future[Boolean] = {
    val q = sql"select count(*) from operator where password = $password"
    dbConfig.db.run(q.as[Int]).map(_.sum > 0)
  }

  def listAll: Future[Seq[User]] = {
    val q = sql"select * from operator"
    implicit val getUserResult: AnyRef with GetResult[User] = GetResult(r => User(Some(r.nextLong), r.nextString, r.nextString, Some(r.nextString), Some(r.nextString)))

    dbConfig.db.run(q.as[User])
  }

}
