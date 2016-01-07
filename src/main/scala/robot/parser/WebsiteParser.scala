package robot.parser

import java.io.File
import scala.util.matching.Regex

class WebsiteParser {

  def run(text: String): String = {
    var cleanText = processTextBeforeRegex(text)
    //"\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"
    var websitePattern = "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"
    var website = processMatching(cleanText, websitePattern)

    return website
  }

  private def processTextBeforeRegex(content: String): String = {
    var cleanText = content.toLowerCase.replaceAll("[^a-zA-Z0-9\\_\\.\\@\\-\\n\\ ]", "").replaceAll("(\\n)+", " ")
    //cleanText = cleanText.replaceAll("[iIl|]", "1")
    return cleanText
  }

  def processMatching(text: String, pattern: String): String = {
    var set = (new Regex(pattern) findAllIn text).toSet
    return (set.mkString("  ;  ") + " ||| ")
  }

}