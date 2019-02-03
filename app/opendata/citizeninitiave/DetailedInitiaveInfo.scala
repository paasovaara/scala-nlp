package opendata.citizeninitiave

import play.api.libs.json.{Json, OWrites, Reads}
import InitiaveInfo._

/**
  * Full Schema is here: https://www.kansalaisaloite.fi/api/#properties
  */
case class DetailedInitiaveInfo (
  id :String, //URL
  stateDate: String, //ISO timestamp
  supportCount: Int,
  name: TranslatedString,
  proposal: TranslatedString,
  rational: Option[TranslatedString]
)

object DetailedInitiaveInfo {
  type DetailedInitiaveListing = Seq[DetailedInitiaveInfo]

  implicit val infoReads: Reads[DetailedInitiaveInfo] = Json.reads[DetailedInitiaveInfo]
  implicit val infoWrites: OWrites[DetailedInitiaveInfo] = Json.writes[DetailedInitiaveInfo]
}
