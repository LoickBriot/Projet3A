package controllers.application     
     
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
           
 
class contactController extends Controller { 
    
    def contactAction = Action {
        Ok(views.html.contactPage("Contact Page","Contact"))
    }     
 
}

