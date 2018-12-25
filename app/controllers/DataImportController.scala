package controllers

import javax.inject.Inject
import opendata.citizeninitiave.CitizenInitiveService
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc.Results

import scala.concurrent.ExecutionContext.Implicits.global


class DataImportController @Inject()(cc: ControllerComponents, citizenInitiveService: CitizenInitiveService) extends AbstractController(cc) {
  def listInitives() = Action.async {
    val listingFuture = citizenInitiveService.getAllInitiaves()
    listingFuture.map(listing => {
      Ok(s"Got ${listing.size} listings")
    }) recover {
      case t: Throwable => InternalServerError("Failed: " + t.getMessage)
    }
  }
}
