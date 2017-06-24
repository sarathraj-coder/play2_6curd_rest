package models

/**
  * Created by sarathraj on 23/06/17.
  */
import play.api.libs.json.{JsError, JsSuccess, Json, _}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

class Bad(val status: Boolean = false, val code: Option[Int], val message: String, val data: JsValue)

object Bad {

  def apply(code: Option[Int] = None, message: String) = new Bad(false, code, message, JsString(message))
  def apply(code: Option[Int], message: String, data: JsValue) = new Bad(false, code, message, data)
  def apply(message: String, data: JsValue) = new Bad(false, None, message, data)
  def unapply(bad: Bad) = Some((bad.status, bad.code, bad.message, bad.data))


  implicit val restFormat: Format[Bad] = {
    val reader: Reads[Bad] = (
      (__ \ "code").readNullable[Int] ~
        (__ \ "message").read[String] ~
        (__ \ "data").read[JsValue])(Bad.apply(_, _, _))

    val writer: Writes[Bad] = (
      (__ \ "status").write[Boolean] ~
        (__ \ "code").writeNullable[Int] ~
        (__ \ "message").write[String] ~
        (__ \ "data").write[JsValue])(unlift(Bad.unapply _))

    Format(reader, writer)
  }

}


class Good(val message: String, val data: JsValue) {
  val status = true
  val code = 200
}


object Good {

  def apply(message: String) = new Good(message, JsString(message))
  def apply(message: String, data: JsValue) = new Good(message, data)
  def unapply(good: Good) = Some((good.status, good.code, good.message, good.data))


  implicit val restFormat: Format[Good] = {
    val reads: Reads[Good] = (
      (__ \ "message").read[JsValue]).map(m => Good.apply(m.toString(), m))

    import play.api.libs.json.Writes._
    val writes: Writes[Good] = (
      (__ \ "status").write[Boolean] ~
        (__ \ "code").write[Int] ~
        (__ \ "message").write[String] ~
        (__ \ "data").write[JsValue])(unlift(Good.unapply _))

    Format(reads, writes)
  }

}
