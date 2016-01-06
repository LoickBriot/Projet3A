package controllers.application     
     
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
           
 
class homeController extends Controller { 
    
    def homeAction = Action {
        Ok(views.html.homePage("Home Page","Home"))
    }
   
 
}

