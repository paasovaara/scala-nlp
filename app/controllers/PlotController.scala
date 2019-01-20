package controllers

import vegas._

import javax.inject.Inject
import opendata.citizeninitiave.CitizenInitiaveService
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.Log

class PlotController @Inject()(cc: ControllerComponents, citizenInitiveService: CitizenInitiaveService)
  extends AbstractController(cc) with Log {

  def plotTitles() = Action {
    val plot = Vegas("Country Pop").
      withData(
        Seq(
          Map("country" -> "USA", "population" -> 314),
          Map("country" -> "UK", "population" -> 64),
          Map("country" -> "DK", "population" -> 80)
        )
      ).
      encodeX("country", Nom).
      encodeY("population", Quant).
      mark(Bar)

    val html = plot.html.pageHTML("Title")
    Ok(html).as(HTML)
  }

}
