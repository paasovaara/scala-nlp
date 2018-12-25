package opendata.citizeninitiave

import play.api.libs.json.{Json, OWrites, Reads}


case class TranslatedString(
  fi: Option[String],
  sv: Option[String]
)

case class InitiveInfo(
  id: String,
  totalSupportCount: Int,
  name: TranslatedString
)

object InitiveInfo {
  type InitiveListing = Seq[InitiveInfo]

  implicit val languageReads: Reads[TranslatedString] = Json.reads[TranslatedString]
  implicit val languageWrites: OWrites[TranslatedString] = Json.writes[TranslatedString]

  implicit val infoReads: Reads[InitiveInfo] = Json.reads[InitiveInfo]
  implicit val infoWrites: OWrites[InitiveInfo] = Json.writes[InitiveInfo]
}
