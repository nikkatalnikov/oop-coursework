package controllers

import java.time.Clock

import javax.inject._
import models.User
import play.api.Configuration
import play.api.libs.json.{JsError, JsValue, Json}
import services.UserService

import scala.concurrent.{ExecutionContext, Future}
import pdi.jwt.JwtSession._
import play.api.mvc.{Action, AnyContent}

@Singleton
class ProfileController @Inject()(scc: SecuredControllerComponents, userService: UserService)(implicit ec: ExecutionContext, conf: Configuration)
  extends SecuredController(scc) {

  implicit val clock: Clock = Clock.systemUTC


  def getAllUsers: Action[AnyContent] = AuthenticatedAction.async { implicit request =>
    userService.listAllUsers map { users =>
      Ok(Json.toJson(users))
    }
  }

  def login: Action[JsValue] = Action.async (parse.json) { implicit request =>
    val result = Json.fromJson[User](request.body).asEither

    result match {
      case Right(userParsed) => userService.checkPass(userParsed.password) map {
        x => if (x) Ok.addingToJwtSession("user", userParsed) else Unauthorized
      }

      case Left(s) => Future(BadRequest(JsError.toJson(s)))
    }
  }

  def signup: Action[JsValue] = Action.async (parse.json) { implicit request =>
    val newUser = Json.fromJson[User](request.body).asEither

    newUser match {
      case Right(user) => userService.addUser(user) map { _ => Ok }
      case Left(s) => Future(BadRequest(JsError.toJson(s)))
    }
  }
}
