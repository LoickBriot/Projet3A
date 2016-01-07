package robot.parser

import java.io.File
import scala.util.matching.Regex

class PriceParser {

  def run(text: String): String = {
    var cleanText = processTextBeforeRegex(text)

    var priceInfo = "(entree libre|gratuit)"
    var numberPrice = " (\\d{1,3})"
    var currencyType = "(| )(e|euro|euros|€|dollard|dollards|$)"
    
    var price = processMatching(cleanText, numberPrice + currencyType)
    
    return price
  }

  private def processTextBeforeRegex(content: String): String = {
    var cleanText = content.toLowerCase.replaceAll("[éèëê]", "e").replaceAll("[àâä]", "a").replaceAll("-", " ").replaceAll("[^a-zA-Z0-9\\€\\$\\-\\ \\n]", "").replaceAll("(\\n)+", " ")
    //cleanText = cleanText.replaceAll("[iIl|]", "1")
    return cleanText
  }

  def processMatching(text: String, pattern: String): String = {
    var set = (new Regex(pattern) findAllIn text).toSet
    return (set.mkString("  ;  ") + " ||| ")
  }

}