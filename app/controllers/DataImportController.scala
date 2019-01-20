package controllers

import javax.inject.Inject
import opendata.citizeninitiave.CitizenInitiaveService
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc.Results

import scala.concurrent.ExecutionContext.Implicits.global


class DataImportController @Inject()(cc: ControllerComponents, citizenInitiveService: CitizenInitiaveService) extends AbstractController(cc) {
  def listInitiaves() = Action.async {
    val listingFuture = citizenInitiveService.getAllInitiaves()
    listingFuture.map(listing => {
      Ok(s"Got ${listing.size} listings")
    }) recover {
      case t: Throwable => InternalServerError("Failed: " + t.getMessage)
    }
  }
}
