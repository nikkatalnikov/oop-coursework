package services

import com.google.inject.Inject
import models.{User, Users}

import scala.concurrent.Future

class UserService @Inject() (users: Users) {

  def addUser(user: User): Future[String] = {
    users.add(user)
  }

  def listAllUsers: Future[Seq[User]] = {
    users.listAll
  }

  def checkPass(password: String): Future[Boolean] = {
    users.checkPass(password)
  }
}
