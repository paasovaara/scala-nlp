package opendata.citizeninitiave

import java.net.URL

import javax.inject.Inject
import play.api.Configuration
import services.WebServiceFetcher

import scala.concurrent.Future

class CitizenInitiveService @Inject()(config: Configuration, val fetcher: WebServiceFetcher) {
  val baseUrl = config.get[String]("citizenInitiave.baseUrl")
  lazy val listingEndpoint: URL = new URL(baseUrl + "initiatives")

  def getAllInitiaves(): Future[InitiveInfo.InitiveListing] = {
    fetcher.getAndParseJson[InitiveInfo.InitiveListing](listingEndpoint)
  }

}
