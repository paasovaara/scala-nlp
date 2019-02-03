package opendata.citizeninitiative

import java.net.URL

import javax.inject.Inject
import opendata.citizeninitiative.DetailedInitiativeInfo.DetailedInitiativeListing
import opendata.citizeninitiative.InitiativeInfo.InitiativeListing
import play.api.Configuration
import services.WebServiceFetcher
import utils.Log

import scala.concurrent.{Await, Future}
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class CitizenInitiaveService @Inject()(config: Configuration, val fetcher: WebServiceFetcher) extends Log {
  val baseUrl = config.get[String]("citizenInitiave.baseUrl")
  lazy val listingEndpoint: URL = new URL(baseUrl + "initiatives")

  val limitParameter = "limit"
  val offsetParameter = "offset"
  val defaultLimit = 50

  def getAllInitiaves(): Future[DetailedInitiativeListing] = {
    getIniativesAt(0, defaultLimit, Seq()).map(all => {
      val futuresForDetailedInfos = all.map {
        info => {
          val url = new URL(info.id)
          fetcher.getAndParseJson[DetailedInitiativeInfo](url)
        }
      }
      val combined = Future.sequence(futuresForDetailedInfos)
      Await.result(combined, 30.seconds)

    })
    //TODO error handling
  }

  def getInitiave(id: Int): Future[DetailedInitiativeInfo] = {
    val url = new URL(s"$listingEndpoint/$id")
    fetcher.getAndParseJson[DetailedInitiativeInfo](url)
  }

  private def getIniativesAt(offset: Int, limit: Int, aggregate: InitiativeListing): Future[InitiativeInfo.InitiativeListing] = {
    val params = parameters(limit, offset)
    val newOnes = fetcher.getAndParseJson[InitiativeInfo.InitiativeListing](listingEndpoint, params)
    newOnes.flatMap {
      fetched => {
        debug(s"received ${fetched.size} new messages for offset $offset")
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
