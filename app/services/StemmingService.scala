package services

import org.tartarus.snowball.ext.finnishStemmer
import utils.Log

class StemmingService extends Log {
  val stemmer = new finnishStemmer()

  def stem(orig: String) = {
    val stemmedSeq = orig.split(" ").map(
      word => {
        stemmer.setCurrent(word)
        stemmer.stem()
        stemmer.getCurrent
      }
    ).toSeq
    stemmedSeq.mkString(" ")
  }
}
