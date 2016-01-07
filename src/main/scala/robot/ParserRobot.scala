package robot

import models.DAO._
import models.Handler._
import robot.imageprocessing._
import robot.parser._
import java.io.File

object ParserRobot {

  private val rootPath: String = System.getProperties().get("user.dir").toString()
  private val relativePathToUploadFile: String = "src" + File.separator + "main" + File.separator + "public" + File.separator + "tmp"
  private val absolutePathToUploadFile: String = rootPath + File.separator + relativePathToUploadFile

  def main(args: Array[String]): Unit = {

    val posterDAO = new PosterDAO()
    val posterHandler = new PosterHandler()
    val textExtractor = new TextExtractor()
    val dateParser = new DateParser()
    val locationParser = new LocationParser()
    val priceParser = new PriceParser()
    val websiteParser = new WebsiteParser()
    val emailParser = new EmailParser()
    val phoneParser = new PhoneParser()

    while (true) {
      var posterToProcess_List = posterDAO.findByProcessBool(false).toList
      //println(posterToProcess_List)

      for (poster <- posterToProcess_List) {
        try {

          var path = absolutePathToUploadFile + File.separator + posterHandler.getUniqueName(poster)

          var text = textExtractor.run(path)
          var date = dateParser.run(text)
          var location = locationParser.run(text)
          var price = priceParser.run(text)
          var website = websiteParser.run(text)
          var email = emailParser.run(text)
          var phone = phoneParser.run(text)

          var updatedRow = posterHandler.newRow(poster.id, posterHandler.getInputName(poster), posterHandler.getUniqueName(poster), text, date, location, price, website, email, phone, true)
          posterDAO.update(updatedRow)
        } catch {
          case e: Throwable => println("Problem with id:" + poster.id + ", name:" + poster.inputname)
        }

      }

      Thread sleep 10000
    }

  }

}