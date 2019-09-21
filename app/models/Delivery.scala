package models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{Format, JsString, JsSuccess, JsValue, Json, OFormat}
import slick.jdbc.{GetResult, JdbcProfile}

import scala.concurrent.{ExecutionContext, Future}

trait Delivery {
  def id: Option[Long]
  def title: String
  def recipientNumber: Long
}

case class DeliveryFull(id: Option[Long], title: String, recipientNumber: Long, createdAt: Timestamp, district: District, courier: Courier) extends Delivery
case class DeliveryShort(id: Option[Long], title: String, recipientNumber: Long, district_id: Long, courier_id: Long) extends Delivery

object DeliveryFull {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss")
    def reads(json: JsValue): JsSuccess[Timestamp] = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp): JsValue = JsString(format.format(ts))
  }

  implicit val deliveryFormat: OFormat[DeliveryFull] = Json.format[DeliveryFull]
}

object DeliveryShort {
  implicit val deliveryFormat: OFormat[DeliveryShort] = Json.format[DeliveryShort]
}


import slick.jdbc.MySQLProfile.api._

class Deliveries @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  def listAllDeliveries: Future[Seq[DeliveryFull]] = {
    val q = sql"select * from parcel left join district on parcel.district_id = district.id left join courier on parcel.courier_id = courier.id"

    implicit val getUserResult: AnyRef with GetResult[DeliveryFull] = {
      GetResult(r => {
        val id = r.nextLongOption
        val title = r.nextString
        val number = r.nextLong
        val ts = r.nextTimestamp

        r.skip
        r.skip

        val district = District(r.nextLongOption, r.nextString, r.nextString)
        val courier = Courier(r.nextLongOption, r.nextStringOption, r.nextStringOption)

        DeliveryFull(id, title, number, ts, district, courier)
      })
    }

    dbConfig.db.run(q.as[DeliveryFull])
  }

  def editDelivery(delivery: DeliveryShort): Future[String] = {
    val q = sqlu"update parcel set title = ${delivery.title}, recipient_number = ${delivery.recipientNumber}, courier_id = ${delivery.courier_id}, district_id = ${delivery.district_id} where id = ${delivery.id.get}"
    dbConfig.db.run(q).map(_ => "Successfully updated").recover {
      case ex: Exception => ex.getMessage
    }
  }

  def createDelivery(delivery: DeliveryShort): Future[String] = {
    val q = sqlu"insert into parcel (title, recipient_number, courier_id, district_id) values (${delivery.title}, ${delivery.recipientNumber}, ${delivery.courier_id}, ${delivery.district_id})"
    dbConfig.db.run(q).map(_ => "Successfully created").recover {
      case ex: Exception => ex.getMessage
    }
  }

  def deleteDelivery(deliveryId: Long): Future[String] = {
    val q = sqlu"delete from parcel where id = $deliveryId"
    dbConfig.db.run(q).map(_ => "Successfully deleted").recover {
      case ex: Exception => ex.getMessage
    }
  }
}
