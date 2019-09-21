package models

import play.api.libs.json.{Json, OFormat}

case class District(id: Option[Long], title: String, city: String)
object District {
  implicit val format: OFormat[District] = Json.format[District]
}
