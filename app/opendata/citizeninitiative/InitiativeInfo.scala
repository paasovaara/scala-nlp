package opendata.citizeninitiative

import play.api.libs.json.{Json, OWrites, Reads}


case class TranslatedString(
  fi: Option[String],
  sv: Option[String]
)

case class InitiativeInfo(
  id: String,
  totalSupportCount: Int,
  name: TranslatedString
)

object InitiativeInfo {
  type InitiativeListing = Seq[InitiativeInfo]

  implicit val languageReads: Reads[TranslatedString] = Json.reads[TranslatedString]
  implicit val languageWrites: OWrites[TranslatedString] = Json.writes[TranslatedString]

  implicit val infoReads: Reads[InitiativeInfo] = Json.reads[InitiativeInfo]
  implicit val infoWrites: OWrites[InitiativeInfo] = Json.writes[InitiativeInfo]
}
