package utils

import play.api.Logger

trait Log {
  val logger: Logger = Logger(this.getClass())

  def debug(msg: String) = logger.debug(msg)

  def info(msg: String) = logger.info(msg)

  def warn(msg: String) = logger.warn(msg)
  def warn(msg: String, t: Throwable) = logger.warn(msg, t)

  def error(msg: String) = logger.error(msg)
  def error(msg: String, t: Throwable) = logger.error(msg, t)

}
