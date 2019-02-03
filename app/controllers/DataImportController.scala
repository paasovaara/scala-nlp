package controllers

import javax.inject.Inject
import models._
import opendata.citizeninitiative.CitizenInitiaveService
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc.Results
import utils.Log

import scala.concurrent.ExecutionContext.Implicits.global


class DataImportController @Inject()(cc: ControllerComponents, citizenInitiveService: CitizenInitiaveService)
  extends AbstractController(cc) with Log {

  def listInitiaves() = Action.async {
    val listingFuture = citizenInitiveService.getAllInitiaves()
    listingFuture.map(listing => {
      val fullDataListing = listing.map(FullData(_))

      val full = FullDataListing(fullDataListing.size, fullDataListing)

      Ok(Json.toJson(full))
    }) recover {
      case t: Throwable => {
        error("Error at listing initiatives", t)
        InternalServerError("Failed: " + t.getMessage)
      }
    }
  }

  def initiave(id: Int) = Action.async {
    citizenInitiveService.getInitiave(id).map(info => {
      Ok(Json.toJson(FullData(info)))
    }) recover {
      case t: Throwable => {
        error(s"Error at getting initiative $id", t)
        InternalServerError("Failed: " + t.getMessage)
      }
    }
  }

  def listTitles() = Action.async {
    val listingFuture = citizenInitiveService.getAllInitiaves()
    listingFuture.map(listing => {
      val titles: Seq[Title] = listing.flatMap(_.name.fi).map(Title(_))

      Ok(Json.toJson(titles))
    }) recover {
      case t: Throwable => {
        error("Error at listing titles", t)
        InternalServerError("Failed: " + t.getMessage)
      }
    }
  }
}
