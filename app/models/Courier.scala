package models

import play.api.libs.json.{Json, OFormat}

case class Courier(id: Option[Long], name: Option[String], surname: Option[String]) extends Person

object Courier {
  implicit val format: OFormat[Courier] = Json.format[Courier]
}
