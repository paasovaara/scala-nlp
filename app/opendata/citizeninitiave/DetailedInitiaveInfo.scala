package opendata.citizeninitiave

import play.api.libs.json.{Json, OWrites, Reads}
import InitiaveInfo._

case class DetailedInitiaveInfo (
  id :String, //URL
  //stateDate: String, //ISO timestamp
  //supportCount: Int,
  name: TranslatedString,
  //proposal: TranslatedString,
  //rational: TranslatedString
)

object DetailedInitiaveInfo {
  type DetailedInitiaveListing = Seq[DetailedInitiaveInfo]

  implicit val infoReads: Reads[DetailedInitiaveInfo] = Json.reads[DetailedInitiaveInfo]
  implicit val infoWrites: OWrites[DetailedInitiaveInfo] = Json.writes[DetailedInitiaveInfo]
}
