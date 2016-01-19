package robot.parser

import java.io.File
import scala.util.matching.Regex

class PriceParser {

  def run(text: String): String = {

    var map = Map[String, Int]()

    // score 35
    var cleanText = processTextBeforeRegex(text)
    var priceInfo = "(entree libre|gratuit)"
    var numberPrice = " (\\d{1,3})"
    var currencyType = "(| )(e)"
    var price = processMatching(cleanText, numberPrice + currencyType)
    price.split(" ; ").foreach { el => if (el != "") map += (el -> 35) }

    // score 75  
    priceInfo = "(entree libre|gratuit)"
    numberPrice = " (\\d{1,3})"
    currencyType = "(| )(euro|euros|€|dollard|dollards|$)"
    price = processMatching(cleanText, numberPrice + currencyType)
    price += processMatching(cleanText, priceInfo)
    price.split(" ; ").foreach { el => if (el != "") map += (el -> 75) }

    // score 100
    price = processMatching(text, numberPrice + currencyType)
    price += processMatching(text, priceInfo)
    price.split(" ; ").foreach { el => if (el != "") map += (el -> 100) }

    return getResultInShape(map)
  }

  def getResultInShape(map: Map[String, Int]): String = {
    var result = ""
    var mapResult = Map[Int, String]()
    var sortMap = map.toSeq.sortBy(_._2).sortWith(_._2 > _._2).toMap
    sortMap.foreach { el =>
      if (mapResult.exists(_._1 == el._2)) {
        mapResult += (el._2 -> (mapResult(el._2) + "; " + el._1))
      } else {
        mapResult += (el._2 -> el._1)
      }
    }
    mapResult.foreach { el =>
      result += el._1 + " : " + el._2 + " || "
    }

    result = if (result.length > 3) result.substring(0, result.length-3) else result

    return result
  }

  private def processTextBeforeRegex(content: String): String = {
    var cleanText = content.toLowerCase.replaceAll("[éèëê]", "e").replaceAll("[àâä]", "a").replaceAll("-", " ").replaceAll("[^a-zA-Z0-9\\€\\$\\-\\ \\n\\|]", "").replaceAll("(\\n)+", " ")
    cleanText = cleanText.replaceAll("[I|]", "1")
    cleanText = cleanText.replaceAll("[sS]", "5")
    return cleanText
  }

  def processMatching(text: String, pattern: String): String = {
    var set = (new Regex(pattern) findAllIn text).toSet
    return (set.mkString(" ; "))
  }

}
