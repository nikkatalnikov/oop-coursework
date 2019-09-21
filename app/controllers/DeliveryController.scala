package controllers

import java.time.Clock

import javax.inject._
import models.{Delivery, DeliveryFull, DeliveryShort}
import play.api.Configuration
import play.api.libs.json.{JsError, JsValue, Json}
import services.DeliveryService

import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.{Action, AnyContent}

@Singleton
class DeliveryController @Inject()(scc: SecuredControllerComponents, deliveryService: DeliveryService)(implicit ec: ExecutionContext, conf: Configuration)
  extends SecuredController(scc) {

  implicit val clock: Clock = Clock.systemUTC


  def listDeliveries: Action[AnyContent] = AuthenticatedAction.async { implicit request =>
    deliveryService.listAllDeliveries map { deliveries =>
      Ok(Json.toJson(deliveries))
    }
  }

  def createDelivery: Action[JsValue] = AuthenticatedAction.async (parse.json) { implicit request =>
    val newDelivery = Json.fromJson[DeliveryShort](request.body).asEither

    newDelivery match {
      case Right(d) => deliveryService.createDelivery(d) map { _ => Ok }
      case Left(s) => Future(BadRequest(JsError.toJson(s)))
    }
  }

  def editDelivery: Action[JsValue] = AuthenticatedAction.async (parse.json) { implicit request =>
    val newDelivery = Json.fromJson[DeliveryShort](request.body).asEither

    newDelivery match {
      case Right(d) => deliveryService.editDelivery(d) map { _ => Ok }
      case Left(s) => Future(BadRequest(JsError.toJson(s)))
    }
  }

  def deleteDelivery: Action[JsValue] = AuthenticatedAction.async (parse.json) { implicit request =>
    val deliveryId = Json.fromJson[Long](request.body).asEither

    deliveryId match {
      case Right(d) => deliveryService.deleteDelivery(d) map { _ => Ok }
      case Left(s) => Future(BadRequest(JsError.toJson(s)))
    }
  }
}
