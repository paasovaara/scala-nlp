package opendata.citizeninitiave

import java.net.URL

import javax.inject.Inject
import opendata.citizeninitiave.InitiaveInfo.InitiaveListing
import play.api.Configuration
import services.WebServiceFetcher

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CitizenInitiaveService @Inject()(config: Configuration, val fetcher: WebServiceFetcher) {
  val baseUrl = config.get[String]("citizenInitiave.baseUrl")
  lazy val listingEndpoint: URL = new URL(baseUrl + "initiatives")

  val limitParameter = "limit"
  val offsetParameter = "offset"
  val defaultLimit = 50

  def getAllInitiaves(): Future[InitiaveInfo.InitiaveListing] = {
    val all = getIniativesAt(0, defaultLimit, Seq())
    //TODO error handling

    all
  }

  private def getIniativesAt(offset: Int, limit: Int, aggregate: InitiaveListing): Future[InitiaveInfo.InitiaveListing] = {
    val params = parameters(limit, offset)
    val newOnes = fetcher.getAndParseJson[InitiaveInfo.InitiaveListing](listingEndpoint, params)
    newOnes.flatMap {
      fetched => {
        if (fetched.size < limit) {
          //We've received all, no need to continue the recursion
          Future.successful(aggregate ++ fetched)
        }
        else {
          //There might be more!
          getIniativesAt(offset + limit, limit, aggregate ++ fetched)
        }
      }
    }
  }

  private def parameters(limit: Int, offset: Int) = {
    Seq(
      (limitParameter, limit.toString),
      (offsetParameter, offset.toString)
    )
  }

}
