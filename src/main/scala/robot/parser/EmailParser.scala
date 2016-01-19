package robot.parser

import java.io.File
import scala.util.matching.Regex

class EmailParser {

  def run(text: String): String = {
    var cleanText = processTextBeforeRegex(text)
    //"\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"
    var emailPattern = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

    var email = processMatching(cleanText, emailPattern)

    return email
  }

  private def processTextBeforeRegex(content: String): String = {
    var cleanText = content.toLowerCase.replaceAll("[^a-zA-Z0-9\\_\\~\\—\\.\\@\\-\\n\\ ]", "").replaceAll("(\\n)+", " ").replaceAll("[—~]", "-")
    cleanText = cleanText.replaceAll("[|]", "l")
    return cleanText
  }

  def processMatching(text: String, pattern: String): String = {
    var set = (new Regex(pattern) findAllIn text).toSet
    return (set.mkString("  ;  ") + " ||| ")
  }

}