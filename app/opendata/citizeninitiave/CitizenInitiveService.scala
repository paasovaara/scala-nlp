package opendata.citizeninitiave

import java.net.URL

import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.libs.ws.WSResponse

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class CitizenInitiveService @Inject()(config: Configuration, implicit val wsClient: WSClient) {
  val baseUrl = config.get[String]("citizenInitiave.baseUrl")
  lazy val listingEndpoint: URL = new URL(baseUrl + "initiatives")

  val timeout: Long = 10000L

  def getAllInitiaves(): Future[InitiveInfo.InitiveListing] = {


    val request = wsClient.url(listingEndpoint.toString)
      .withFollowRedirects(true)
      .withRequestTimeout(timeout.millis)

    request.get() map {
      response => {
        if (response.status == 200) {
           print(response.body)
           parseCaseClassFromJson[InitiveInfo.InitiveListing](response.body)
        }
        else {
          throw new Exception("Failed to get Initives")
        }
      }
    }
  }

  def parseCaseClassFromJson[T](content: String)(implicit reads:Reads[T]): T = {
    val js = Json.parse(content)
    val jsRequest: JsResult[T] = js.validate[T]
    jsRequest match {
      case JsSuccess(caseClass, _) => {
        caseClass
      }
      case JsError(errors) => {
        throw new Exception("Could not parse Json into a class")
      }
    }
  }
}
