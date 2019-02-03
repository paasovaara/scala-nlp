import play.api.libs.json.{Json, OWrites}

package object models {
  implicit val titleWrites: OWrites[Title] = Json.writes[Title]
  implicit val fullDataWrites: OWrites[FullData] = Json.writes[FullData]
  implicit val fullListingWrites: OWrites[FullDataListing] = Json.writes[FullDataListing]

  implicit val stemmedWrites: OWrites[Stemmed] = Json.writes[Stemmed]

}
