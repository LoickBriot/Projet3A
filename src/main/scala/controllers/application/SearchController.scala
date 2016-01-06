package controllers.application


import play.api._
import play.api.mvc._
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import models.DAO._

class SearchController extends Controller {
  
  /*
    * Initialisation des paths : root, folder d'upload (paths relatif at absolu)
    */
  val rootPath: String = System.getProperties().get("user.dir").toString()
  val relativePathToUploadFile: String = "src" + File.separator + "main" + File.separator + "public" + File.separator + "tmp"
  val absolutePathToUploadFile: String = rootPath + File.separator + relativePathToUploadFile
  val file: File = new File(absolutePathToUploadFile)
  if (!file.exists()) file.mkdirs();

  /* 
    * Actions pour la page de recherche
    */
  def searchAction = Action {

    var uniqueName_fileList = getUniqueNameList()
    var image_path = File.separator + "assets" + File.separator + "tmp" + File.separator + uniqueName_fileList(0)
    var image_index = 0
    var name_list = getNameField()
    var value_list = getValueField(uniqueName_fileList(0))

    Ok(views.html.searchPage("Search Page", uniqueName_fileList, image_path, image_index, name_list, value_list))
  }

  def searchPostAction = Action(parse.multipartFormData) { request =>

    var uniqueName_fileList = getUniqueNameList()
    var image_path = File.separator + "assets" + File.separator + "tmp" + File.separator + request.body.dataParts.get("posterName").head(0)
    var image_index = uniqueName_fileList.indexOf(request.body.dataParts.get("posterName").head(0))
    var name_list = getNameField()
    var value_list = getValueField(request.body.dataParts.get("posterName").head(0))

    Ok(views.html.searchPage("Search Page", uniqueName_fileList, image_path, image_index, name_list, value_list))
  }

  def getUniqueNameList(): List[String] = {
    var posterDAO = new PosterDAO()
    var result = posterDAO.findAll().toList
    var uniqueName_fileList = result.map { x => x.uniquename.getOrElse("") }
    if ( uniqueName_fileList.isEmpty)  uniqueName_fileList:+="defaut_image.jpg"
    return uniqueName_fileList
  }

  def getNameField(): List[String] = {
    return List[String]("Nom", "Texte", "Date", "Lieu", "Prix", "Site Internet", "Courriel", "Telephone")
  }

  def getValueField(imageName: String): List[String] = {
    var result_list = List[String]()
    try {
      var posterDAO = new PosterDAO()
      result_list = posterDAO.findByUniqueName(imageName).flatMap(p => List(p.inputname.getOrElse(""), p.text.getOrElse(""), p.date.getOrElse(""), p.location.getOrElse(""), p.price.getOrElse(""), p.website.getOrElse(""), p.email.getOrElse(""), p.phone.getOrElse(""))).toList

      if (result_list.isEmpty) result_list = List[String]("n.c", "n.c", "n.c", "n.c", "n.c", "n.c", "n.c", "n.c")

    } catch {
      case e: Throwable => println("getUniqueNameListInDB")
    }

    return result_list
  }

}

