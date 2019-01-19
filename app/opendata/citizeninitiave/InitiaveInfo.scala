package opendata.citizeninitiave

import play.api.libs.json.{Json, OWrites, Reads}


case class TranslatedString(
  fi: Option[String],
  sv: Option[String]
)

case class InitiaveInfo(
  id: String,
  totalSupportCount: Int,
  name: TranslatedString
)

object InitiaveInfo {
  type InitiaveListing = Seq[InitiaveInfo]

  implicit val languageReads: Reads[TranslatedString] = Json.reads[TranslatedString]
  implicit val languageWrites: OWrites[TranslatedString] = Json.writes[TranslatedString]

  implicit val infoReads: Reads[InitiaveInfo] = Json.reads[InitiaveInfo]
  implicit val infoWrites: OWrites[InitiaveInfo] = Json.writes[InitiaveInfo]
}
