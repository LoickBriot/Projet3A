package robot.parser

import java.io.File
import scala.util.matching.Regex

class PhoneParser{
    
   
   def run(text: String): String = {
    var cleanText = processTextBeforeRegex(text)
    
    var phonePattern ="(0\\d{9}|( 0\\d)( \\d{2}){4})\\b"       
    var phone = processMatching(cleanText, phonePattern)

    return phone
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