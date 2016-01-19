package robot.parser

import java.io.File
import scala.util.matching.Regex
import scala.math
import upickle.default._
import java.io.File
import java.io.FileWriter

object LocationParser {

  val rootPath: String = System.getProperties().get("user.dir").toString()
  val relativeDataPath: String = "src" + File.separator + "main" + File.separator + "scala" + File.separator + "robot" + File.separator + "parser" + File.separator + "data" + File.separator
  val absoluteDataPath: String = rootPath + File.separator + relativeDataPath
  val CITY_LIST_PATH = absoluteDataPath + "villes_france.txt"
  val EXACT_MAP = absoluteDataPath + "exactMap_villes_france.txt"
  val FUZZY_START_END_MAP = absoluteDataPath + "fuzzyStartEndMap_villes_france.txt"
  val FUZZY_ALL_MAP = absoluteDataPath + "fuzzyAllMap_villes_france.txt"

  def main(args: Array[String]): Unit = {

    var locationPars = new LocationParser()
    var time = System.nanoTime()
    var source = scala.io.Source.fromFile(CITY_LIST_PATH, "UTF-8")
    var lines = try source.mkString.toLowerCase.replaceAll("[^a-zA-Z0-9_\\-\\'\\ \\n]", "").replaceAll("-", " ").split("\n") finally source.close()

    locationPars.serializeMap(EXACT_MAP, lines, 0)
    locationPars.serializeMap(FUZZY_START_END_MAP, lines, 1)
    locationPars.serializeMap(FUZZY_ALL_MAP, lines, 2)

    /*
    // Lire le fichier de Regex et effectuer le traitement
    var string = "portland tickelrva sivu lucius dnmino young puritans moon beatrice goth twinning vrai suuns roekumihles kitsune' troumaca ilach london ehristineand carrelnw iourcoing laura cane jaggo grammar toulousu nancy temples rocky cioii lappu snakeheads eamlnun jacco matthew paris half suede wummwummw hollysiz alunageorge lazer mvula june white teleman foals valerie blanche beam papa nantes tuckmm sex- fathers sohn qwummwnmem evervtning boniiiik deep major austra drenge amazing findlay fnac swim gomoaux bretdn oueens gardner everything deptford petite casual years maison christine dubauiznovemhre deftford arthur these noir kitsune truumaca"
    //var string="1 nmmns ozan peron plaine-des-palmistes fm eamlnun seam mam sur relms rappu plaine-des-pajmistes toulouse tuckmm 2 usmmcksm mus mueuene elumuvammzunn esmmrks m wummwummw ill 5 ill 12 nonlllliil ill paris iourcoing cioii nantes boniiiik ilach loulouse  cane blanche voulouse an label lamina kitsune maison en vrai 14 teleman hollysiz the amazing snakeheads half moon run years years sivu deftford goth findlay sir sly md rocky arthur beatrice swim deep sohn casual sex jacco gardner truumaca young fathers portland everything evervtning lucius petite noir matthew e white temples drenge papa ehristineand the llueens london grammar alunageorge these new puritans valerie june bretdn suuns laura mvula major lazer foals suede austra j i wm m festival roekumihles a was mesmm l 7 2 3 7351175 2 w x ai 7 fm v 1 g i major lazer foals suede austra valerie june bretdn suuns laura mvula alunageorge these new puritans christine and the oueens london grammar petite noir matthew e white temples drenge papa troumaca young fathers portland everything everything lucius arthur beatrice swim deep sohn casual sex jaggo gardner years years sivu deptford goth findlay sir sly md rocky teleman hollysiz the amazing snakeheads half moon run cane blanche au label dnmino kitsune maison en vrai 14 paris twinning can nam gomoaux nancy toulousu dubauiznovemhre 2013 qwummwnmem inins mueuui elprngvamszinn lesmmrks nm anauans fnac carrelnw beam lnamtnm sur lappu tickelrva 9 usmmcksxnm"
    
    time = System.nanoTime()
    println(locationPars.exactMatch(read[List[String]](scala.io.Source.fromFile(EXACT_MAP, "UTF-8").mkString), string))
    println("Exact: " + (System.nanoTime() - time) / 1000000 + " ms\n")

    time = System.nanoTime()
    println(locationPars.fuzzyMatch(read[Map[String, String]](scala.io.Source.fromFile(FUZZY_START_END_MAP, "UTF-8").mkString), string, 1))
    println("Fuzzy ES: " + (System.nanoTime() - time) / 1000000 + " ms\n")

    time = System.nanoTime()
    println(locationPars.fuzzyMatch(read[Map[String, String]](scala.io.Source.fromFile(FUZZY_ALL_MAP, "UTF-8").mkString), string, 1))
    println("Fuzzy ALL: " + (System.nanoTime() - time) / 1000000 + " ms\n")
*/
  }
}

//matching exact si mot <=3 lettre
class LocationParser {

  val rootPath: String = System.getProperties().get("user.dir").toString()
  val relativeDataPath: String = "src" + File.separator + "main" + File.separator + "scala" + File.separator + "robot" + File.separator + "parser" + File.separator + "data" + File.separator
  val absoluteDataPath: String = rootPath + File.separator + relativeDataPath
  val CITY_LIST_PATH = absoluteDataPath + "villes_france.txt"
  val EXACT_MAP = absoluteDataPath + "exactMap_villes_france.txt"
  val FUZZY_START_END_MAP = absoluteDataPath + "fuzzyStartEndMap_villes_france.txt"
  val FUZZY_ALL_MAP = absoluteDataPath + "fuzzyAllMap_villes_france.txt"

  def run(text: String): String = {

    var cleanText = processTextBeforeRegex(text)
    var map = Map[String, Int]()

    // score 35
    var resultMap = fuzzyMatch(read[Map[String, String]](scala.io.Source.fromFile(FUZZY_ALL_MAP, "UTF-8").mkString), cleanText, 1)
    resultMap.values.flatMap { x => x }.foreach { el => if (el != "") map += (el -> 35) }

    // score 75  
    resultMap = fuzzyMatch(read[Map[String, String]](scala.io.Source.fromFile(FUZZY_START_END_MAP, "UTF-8").mkString), cleanText, 1)
    resultMap.values.flatMap { x => x }.foreach { el => if (el != "") map += (el -> 75) }

    // score 100
    var exactMap = exactMatch(read[List[String]](scala.io.Source.fromFile(EXACT_MAP, "UTF-8").mkString), cleanText)
    exactMap.foreach { el => if (el != "") map += (el -> 100) }

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
    var cleanText = content.toLowerCase.replaceAll("[^a-zA-Z0-9_\\-\\'\\ \\n]", "").replaceAll("\\n", " ").replaceAll("-", " ")

    return cleanText
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
          if (el.length > 3) {
            map ++= Map[String, String](el -> ("(\\b" + "\\p{Lower}" + el.substring(1, el.length) + "\\b)|(\\b" + el.substring(0, el.length - 1) + "\\p{Lower}\\b)"))
          } else {
            map ++= Map[String, String](el -> (s"\\b$el\\b"))
          }
        }
        case 2 => {
          var sb = new StringBuilder
          if (el.length > 3) {
            for (i <- 0 until el.length) {
              sb.append("(\\b" + el.substring(0, i) + "\\p{Lower}" + el.substring(i + 1, el.length) + "\\b)|")
            }
            map ++= Map[String, String](el -> sb.toString.substring(0, sb.length - 1))
          } else {
            map ++= Map[String, String](el -> (s"\\b$el\\b"))
          }
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