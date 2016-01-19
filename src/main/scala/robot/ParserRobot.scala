package robot
 
import models.DAO._
import models.Handler._
import robot.imageprocessing._
import robot.parser._
import java.io.File
import java.io.PrintWriter
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io._
import java.net.InetSocketAddress
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }


object ParserRobot {

  var portNumber = 19370
  var ipAddress = "127.0.0.1"
  private val rootPath: String = System.getProperties().get("user.dir").toString()
  private val relativePathToUploadFile: String = "src" + File.separator + "main" + File.separator + "public" + File.separator + "tmp"
  private val absolutePathToUploadFile: String = rootPath + File.separator + relativePathToUploadFile
   
  def main(args: Array[String]): Unit = {

    val posterDAO = new PosterDAO()
    val posterHandler = new PosterHandler()
    var i = 0
    while (true) {
      var posterToProcess_List = posterDAO.findByProcessBool(true).toList
      //println(posterToProcess_List)

      var socketManager = new SocketManager()
      for (poster <- posterToProcess_List) {
        try {
          var path = absolutePathToUploadFile + File.separator + posterHandler.getUniqueName(poster)

          var toAddr = new InetSocketAddress(ipAddress, portNumber);

          socketManager.sendMessage(toAddr, path)
          //println("send")
          i = socketManager.receiveMessage(toAddr).toInt
          //println("receive")
          if (i != 0) {
            Future {
              var k = i
              var text = ""
              val postDAO = new PosterDAO()
              val postHandler = new PosterHandler()
              val textExtractor = new TextExtractor()
              val dateParser = new DateParser()
              val locationParser = new LocationParser()
              val priceParser = new PriceParser()
              val websiteParser = new WebsiteParser()      
              val emailParser = new EmailParser()
              val phoneParser = new PhoneParser()
              try {

                println(s"this will run in a new thread listening on port $k")
                var threadAddr = new InetSocketAddress(ipAddress, k);
                Thread sleep 2000;
                text = socketManager.receiveMessage(threadAddr)

                var posterEl = poster
                var date = dateParser.run(text)
                var location = locationParser.run(text)
                var price = priceParser.run(text)
                var website = websiteParser.run(text)
                var email = emailParser.run(text)
                var phone = phoneParser.run(text)

                var updatedRow = postHandler.newRow(postHandler.getID(posterEl), posterHandler.getInputName(posterEl), postHandler.getUniqueName(posterEl), text, date, location, price, website, email, phone, true)
                //println("before update")
                postDAO.update(updatedRow)
                println(s"${posterEl.id} updated")  
              } catch {
                case e: Throwable => println(s"$e => Problem with id:" + poster.id + ", name:" + poster.inputname)
              }
              println(s"Thread $k : " + text)
            }

          } else {
            //println("retry")
          }

        } catch {
          case e: Throwable => println(s"$e: Error in loop")
        }
        //println(i)
      }
      Thread sleep 10000
    }

  }

}