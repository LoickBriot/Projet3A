package controllers.application     
     
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.Play.current
import java.io.File
import java.util.UUID

class aboutController extends Controller { 
    
    

    def aboutAction = Action {
        Ok(views.html.aboutPage("About Page","About"))
    }
     
}

