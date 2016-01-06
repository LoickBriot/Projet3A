package controllers.application

import play.api._
import play.api.mvc._
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import models.DAO._
import models.Handler._


class addController extends Controller {

  /*
    * Initialisation des paths : root, folder d'upload (paths relatif at absolu)
    */
  val sep = File.separator
  val rootPath: String = System.getProperties().get("user.dir").toString()
  val relativePathToUploadFile: String = "src" + File.separator + "main" + File.separator + "public" + File.separator + "tmp"
  val absolutePathToUploadFile: String = rootPath + File.separator + relativePathToUploadFile
  val file: File = new File(absolutePathToUploadFile)
  if (!file.exists()) file.mkdirs();

  /*
    * Actions pour la page d'upload
    */

  def addAction = Action {
    Ok(views.html.addPage(""))
  }

  def uploadAction = Action(parse.multipartFormData) { request =>

    var result = ""

    try {
      val filesList = request.body.files.toList
      
      if (filesList.nonEmpty) {
        val posterDAO = new PosterDAO()
        val posterHandler = new PosterHandler()

        for (image <- filesList) {

          /* Save the poster in the temp folder with an unique name */
          var inputName: String = image.filename
          var uniqueName = generateAnUniqueName(inputName);
          image.ref.moveTo(new File(absolutePathToUploadFile + File.separator + uniqueName))

          /* Insert the poster in the DB */
          val newObjToInsert = posterHandler.newRow(0, inputName, uniqueName, "", "", "", "", "", "", "", false)          
          posterDAO.insert(newObjToInsert)
        }

        /* Make return comment for user */
        if (filesList.size > 1) {
          result = "Les " + filesList.size + " fichiers séléctionnés ont été uploadés avec succès."
        } else {
          result = "Le fichier '" + filesList(0).filename + "' a été chargé avec succès."
        }

        /* Close the DB connexion */
        posterDAO.close()
      }

    } catch {
      case e: Throwable => result = "Erreur durant le chargement."
    }

    Ok(views.html.addPage(result))
  }

  def getExtension(inputName: String): String = {
    var extension = ""
    if (inputName.lastIndexOf('.') > 0) {
      extension = inputName.substring(inputName.lastIndexOf('.') + 1);
    }
    return extension
  }

  def generateAnUniqueName(imageName: String): String = {

    var extension = getExtension(imageName)
    var name = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension
    try {
      var posterDAO = new PosterDAO()
      var result = posterDAO.findByUniqueName(name).toList
      while (result.nonEmpty) {
        name = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension
        var result = posterDAO.findByUniqueName(name).toList
      }
      posterDAO.close()
    } catch {
      case e: Throwable => println("Erreur in generateAnUniqueName")
    }
    return name
  }

}

