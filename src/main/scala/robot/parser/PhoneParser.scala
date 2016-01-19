package robot.parser

import java.io.File
import scala.util.matching.Regex

class PhoneParser{
   
   def run(text: String): String = {
    var cleanText = processTextBeforeRegex(text)
    
    var phonePattern ="(0\\d{9}|( 0\\d)( \\d{2}){4}|0\\d{3} (\\d{3} ){2})\\b"       
    var phone = processMatching(cleanText, phonePattern)

    return phone
  }

  private def processTextBeforeRegex(content: String): String = {
    var cleanText = content.toLowerCase.replaceAll("[^a-zA-Z0-9\\n\\ ]", "").replaceAll("(\\n)+", " ").replaceAll("[Oo]", "0")
    cleanText = cleanText.replaceAll("[I|]", "1")
    cleanText = cleanText.replaceAll("[sS]", "5")
    return cleanText
  }

  def processMatching(text: String, pattern: String): String = {
    var set = (new Regex(pattern) findAllIn text).toSet
    return (set.mkString("  ;  ") + " ||| ")
  }
    
}