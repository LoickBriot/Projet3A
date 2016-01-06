package controllers.application   
     
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import models.DAO._          
 
class addController extends Controller { 
    
    
    /*
    * Initialisation des paths : root, folder d'upload (paths relatif at absolu)
    */
    val sep = File.separator
    val rootPath: String = System.getProperties().get("user.dir").toString()
    val relativePathToUploadFile: String = "src"+File.separator+"main"+File.separator+"public"+File.separator+"tmp"
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
        try{
            var filesList = request.body.files.toList
            for (image <- filesList){
                  var inputName: String = image.filename
                  var uniqueName = generateAnUniqueName(inputName);
                  result = s"Le fichier ' $inputName ' a été chargé avec succès."
                  image.ref.moveTo(new File(absolutePathToUploadFile + File.separator + uniqueName))
                  
                  
                  var posterDAO = new PosterDAO()
                  posterDAO.insert(0,inputName, uniqueName, "", "", "", "", "", "", "", false)
            }
           if (filesList.size>1){
                result = "Les "+ filesList.size + " fichiers séléctionnés ont été uploadés avec succès."
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
        var name  = UUID.randomUUID().toString().replaceAll("-", "") +"."+ extension
        try{
            val connection = DB.getConnection("projet3A")
            var query = connection.createStatement().executeQuery(s"SELECT id FROM poster WHERE uniqueName = '$name'")
            while (query.isBeforeFirst()) {
              name = UUID.randomUUID().toString().replaceAll("-", "") +"."+ extension
              query = connection.createStatement().executeQuery(s"SELECT id FROM poster WHERE uniqueName = '$name'")
            }
            connection.close()
        } catch {
            case e: Throwable => println("Erreur in generateAnUniqueName")
        }
        return name
    }
    
    def insertImageInDB(inputName: String, uniqueName: String, text: String, date: String, location: String, price: String, website: String, email: String, phone: String){
        try {
          val connection = DB.getConnection("projet3A")
          val prep = connection.prepareStatement(s"INSERT INTO poster (inputName, uniqueName, text, date, location, price, website, email, phone) VALUES ('$inputName', '$uniqueName', '$text', '$date', '$location', '$price', '$website', '$email', '$phone')")
          prep.executeUpdate
          connection.close()
        } catch {
          case e: Throwable => println("Erreur in insertImageInDB")
        }
    }
    
    
  
    
 
}

