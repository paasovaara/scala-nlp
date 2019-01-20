import play.api.libs.json.{Json, OWrites}

package object models {
  implicit val writes: OWrites[Title] = Json.writes[Title]
}
