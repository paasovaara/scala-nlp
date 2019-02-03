package opendata.citizeninitiative

import play.api.libs.json.{Json, OWrites, Reads}
import InitiativeInfo._

/**
  * Full Schema is here: https://www.kansalaisaloite.fi/api/#properties
  */
case class DetailedInitiativeInfo(
  id :String, //URL
  stateDate: String, //ISO timestamp
  supportCount: Int,
  name: TranslatedString,
  proposal: TranslatedString,
  rational: Option[TranslatedString]
)

object DetailedInitiativeInfo {
  type DetailedInitiativeListing = Seq[DetailedInitiativeInfo]

  implicit val infoReads: Reads[DetailedInitiativeInfo] = Json.reads[DetailedInitiativeInfo]
  implicit val infoWrites: OWrites[DetailedInitiativeInfo] = Json.writes[DetailedInitiativeInfo]
}
