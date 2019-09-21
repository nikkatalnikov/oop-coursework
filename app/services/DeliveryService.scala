package services

import com.google.inject.Inject
import models.{Deliveries, DeliveryFull, DeliveryShort}

import scala.concurrent.Future

class DeliveryService @Inject()(deliveries: Deliveries) {

  def listAllDeliveries: Future[Seq[DeliveryFull]] = {
    deliveries.listAllDeliveries
  }

  def editDelivery(delivery: DeliveryShort): Future[String] = {
    deliveries.editDelivery(delivery)
  }

  def createDelivery(delivery: DeliveryShort): Future[String] = {
    deliveries.createDelivery(delivery)
  }

  def deleteDelivery(deliveryId: Long): Future[String] = {
    deliveries.deleteDelivery(deliveryId)
  }
}
