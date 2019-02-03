package services

import java.net.URL

import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSResponse}
import utils.Log

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.language.postfixOps

class WebServiceFetcher @Inject()(config: Configuration)(implicit val wsClient: WSClient, ec: WebServiceExecutionContext) extends Log {

  val timeout: Duration = config.getOptional[Duration]("webServiceFetcher.timeout").getOrElse(10 seconds)

  type QueryParameter = (String, String)

  def getAndParseJson[T](url: URL, parameters: Seq[QueryParameter] = Seq())(implicit reads:Reads[T]): Future[T] = {
    getJson(url, parameters).map(parseCaseClassFromJson[T])
      .recover {
        case t: Throwable => {
          error("Failed to get " + url.toString, t)
          throw t
        }
      }
  }

  def getJson(url: URL, parameters: Seq[QueryParameter] = Seq()): Future[String] = {
    val start = System.currentTimeMillis()
    val request = createRequest(url, parameters)
    debug(s"Sending request to ${url.toString}")
    request.get() map {
      response => {
        debug(s"Got response for request ${url.toString} in ${System.currentTimeMillis() - start}ms")
        if (isSuccess(response)) {
          response.body
        }
        else {
          warn(s"Received status ${response.status} for request $url")
          throw new Exception("Failed to get " + url)
        }
      }
    }
  }


  private def createRequest(url: URL, parameters: Seq[QueryParameter]) = {
    wsClient.url(url.toString)
      .withFollowRedirects(true)
      .withRequestTimeout(timeout)
      .withQueryStringParameters(parameters:_*)
      .addHttpHeaders("Accept" -> "application/json")
  }

  private def isSuccess(response: WSResponse): Boolean = response.status >= 200 && response.status < 300

  private def parseCaseClassFromJson[T](content: String)(implicit reads:Reads[T]): T = {
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
