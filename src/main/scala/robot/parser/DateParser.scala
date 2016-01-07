package robot.parser

import java.io.File
import scala.util.matching.Regex

class DateParser {

  def run(text: String): String = {
    var date = ""
    var line = processTextBeforeRegex(text)
    //println(line)

    var dayPattern = " (lundi|mardi|mercredi|jeudi|vendredi|samedi|dimanche)"
    var dayNumberPattern = " (\\d{1,2})"
    var monthPattern = " (janvier|fevrier|mars|avril|mai|juin|juillet|aout|septembre|octobre|novembre|decembre)"
    var yearPattern = "( (1 9|19|2 0|20){1}\\d{2})"
    var hourPattern = "( (\\d{1,2})(h|(\\d) h (\\d)|h (\\d)|(\\d) h)(\\d{0,2}))"

    date += displayRegex(dayPattern + dayNumberPattern + monthPattern + yearPattern, line)

    if (date == " ||| ") {
      date = displayRegex(dayNumberPattern + monthPattern + yearPattern, line)
      if (date == " ||| ") {
        date = displayRegex(dayPattern + dayNumberPattern + monthPattern, line)
        if (date == " ||| ") {
          date = displayRegex(dayNumberPattern + monthPattern, line)
          if (date == " ||| ") {
            date = displayRegex(yearPattern, line)
          }
        }

      }
    }

    date += displayRegex(hourPattern, line)

    return date
  }

  private def displayRegex(pattern: String, content: String): String = {

    var set = (new Regex(pattern) findAllIn content).toSet
    return (set.mkString("  ;  ") + " ||| ")
  }
  private def processTextBeforeRegex(content: String): String = {
    var res = content.toLowerCase.replaceAll("[éèëê]", "e").replaceAll("[àâä]", "a").replaceAll("-", " ").replaceAll("[^a-zA-Z0-9\\-\\ \\n]", "").replaceAll("(\\n)+", " ")
    res = res.replaceAll("i0", "10").replaceAll("i1", "11").replaceAll("i2", "12").replaceAll("i3", "13").replaceAll("i4", "14").replaceAll("i5", "15").replaceAll("i6", "16").replaceAll("i7", "17").replaceAll("i8", "18").replaceAll("i9", "19")
    res = res.replaceAll("0i", "01").replaceAll("1i", "11").replaceAll("2i", "21").replaceAll("3i", "3i").replaceAll("4i", "41").replaceAll("5i", "51").replaceAll("6i", "61").replaceAll("7i", "71").replaceAll("8i", "81").replaceAll("9i", "91")
    return res

  }

}