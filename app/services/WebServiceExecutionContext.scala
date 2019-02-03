package services

import javax.inject._

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext

/**
  * Thread pool / Execution context for the WebServiceFetcher
  * @param system
  */
@Singleton
class WebServiceExecutionContext @Inject()(system: ActorSystem) extends CustomExecutionContext(system, "webService.dispatcher")
