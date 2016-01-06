package robot.parser

import java.io.File
import scala.util.matching.Regex
import scala.math
import upickle.default._
import java.io.File
import java.io.FileWriter

//matching exact si mot <=3 lettre
class LocationParser{
  
  val rootPath: String = System.getProperties().get("user.dir").toString()
  val relativeDataPath: String = "src" + File.separator + "main" + File.separator + "scala" + File.separator + "robot" + File.separator + "parser" + File.separator + "data" + File.separator
  val absoluteDataPath: String = rootPath + File.separator + relativeDataPath
  val CITY_LIST_PATH = absoluteDataPath + "villes_france.txt"
  val EXACT_MAP =  absoluteDataPath + "exactMap_villes_france.txt"
  val FUZZY_START_END_MAP = absoluteDataPath + "fuzzyStartEndMap_villes_france.txt"
  val FUZZY_ALL_MAP = absoluteDataPath + "fuzzyAllMap_villes_france.txt"

  
    def run(text: String): String ={    
      var cleanText = text.toLowerCase.replaceAll("[^a-zA-Z0-9_\\-\\'\\ \\n]", "").replaceAll("\\-\\n", " ")
      var resultMap = fuzzyMatch(read[Map[String, String]](scala.io.Source.fromFile(FUZZY_START_END_MAP, "UTF-8").mkString), cleanText, 1)
      return resultMap.values.flatMap { x => x }.mkString("; ")
    }
    
   def exactMatch(list: List[String], string: String): List[String] = {
    var result = List[String]()
    list.foreach { x =>
      var index = string.indexOf(x)
      if (index >= 0) result +:= deleteBoundaryWhiteSpace(x)
    }
    return result
  }

  def fuzzyMatch(map: Map[String, String], string: String, mapType: Int = 0): Map[String, Set[String]] = {
    var result = Map[String, Set[String]]()
    var listCity = map.keys.toList
    var listPattern = map.values.toList
    var i = 0
    listPattern.foreach { el =>
      var set = Set[String]()
      el.split("\\|").foreach { pat =>
        var a = (new Regex(pat) findAllIn string).toSet
        a.foreach { y =>
          var x = deleteBoundaryWhiteSpace(y)
          mapType match {
            case 0 => {
              if (result.contains(listCity(i))) result ++= Map[String, Set[String]](listCity(i) -> (Set[String](x) ++ result(listCity(i))))
              else result ++= Map[String, Set[String]](listCity(i) -> Set[String](x))
            }
            case 1 => {
              if (result.contains(x)) result ++= Map[String, Set[String]](x -> (Set[String](listCity(i)) ++ result(x)))
              else result ++= Map[String, Set[String]](x -> Set[String](listCity(i)))
            }
          }
        }
      }
      i += 1
    }
    return result
  }

  def deleteBoundaryWhiteSpace(y: String): String = {
    if (y(0) == ' ' && y(y.length - 1) == ' ') y.substring(1, y.length - 1)
    else if (y(0) == ' ') y.substring(1)
    else if (y(y.length - 1) == ' ') y.substring(0, y.length - 1)
    else y
  }

  def serializeMap(path: String, lines: Array[String], serializationType: Int = 0) {
    var map = Map[String, String]()
    lines.foreach { el =>
      var b = new StringBuilder
      serializationType match {
        case 0 => map ++= Map[String, String](el -> (s" $el "))
        case 1 => {
          map ++= Map[String, String](el -> ("(\\b" + "\\p{Lower}" + el.substring(1, el.length) + "\\b)|(\\b" + el.substring(0, el.length - 1) + "\\p{Lower}\\b)"))
        }
        case 2 => {
          var sb = new StringBuilder
          for (i <- 0 until el.length) {
            sb.append("(\\b" + el.substring(0, i) + "\\p{Lower}" + el.substring(i + 1, el.length) + "\\b)|")
          }
          map ++= Map[String, String](el -> sb.toString.substring(0, sb.length - 1))
        }
      }
    }
    serializationType match {
      case 0     => writeTextFile(path, write(map.values.toList))
      case 1 | 2 => writeTextFile(path, write(map))
    }
  }
  
  
  def writeTextFile(path: String, content: String) {
    var fw = new FileWriter(path, false);
    fw.write(content);
    fw.close()
  }
         
}