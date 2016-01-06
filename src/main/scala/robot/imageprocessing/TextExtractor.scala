package robot.imageprocessing

import java.io.File
import net.sourceforge.tess4j.Tesseract1


class TextExtractor{
  
  //To IMPROVE
    def run(path: String): String ={
      var tess = new Tesseract1(); //
      tess.setDatapath("C:\\Users\\LOICK\\IdeaProjects\\scala\\ExtractDataFromImages\\libs\\Tess4J\\tessdata");    
      var result=""
    try {
      result = tess.doOCR(new File(path));
    } catch {
      case e: Throwable => e.printStackTrace
    }
      return result
    }      
    
}