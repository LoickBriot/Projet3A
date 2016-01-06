package models.Handler


import models.Tables
import models.Tables._
import models.Tables.profile.api._


class PosterHandler {
  
  def newRow(id: Int, inputname: String, uniquename: String, text: String, date: String, location: String, price: String, website: String, email: String, phone: String, processbool: Boolean) : Tables.PosterRow = {
    return Tables.PosterRow(id, Some(inputname), Some(uniquename), Some(text), Some(date), Some(location), Some(price), Some(website), Some(email), Some(phone), processbool)
  }    
  
  /* accessors */
  def getID(obj: Tables.PosterRow): Int={
    return obj.id
  }
  
  def getInputName(obj: Tables.PosterRow): String={
    return obj.inputname.getOrElse("")
  }
  
  def getUniqueName(obj: Tables.PosterRow): String={
    return obj.uniquename.getOrElse("")
  }
  
  def getText(obj: Tables.PosterRow): String={
    return obj.text.getOrElse("")
  }
  
  def getDate(obj: Tables.PosterRow): String={
    return obj.date.getOrElse("")       
  }
  
  def getLocation(obj: Tables.PosterRow): String={
    return obj.location.getOrElse("")
  }
  
  def getPrice(obj: Tables.PosterRow): String={
    return obj.price.getOrElse("")
  }
  
  def getWebsite(obj: Tables.PosterRow): String={
    return obj.website.getOrElse("")
  }
  
  def getEmail(obj: Tables.PosterRow): String={
    return obj.email.getOrElse("")
  }
  
  def getPhone(obj: Tables.PosterRow): String={
    return obj.phone.getOrElse("")
  }
  
  def getProcessBool(obj: Tables.PosterRow): Boolean={
    return obj.processbool
  }
  
  /* mutators */

  def setText(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, Some(newVal), obj.date, obj.location, obj.price, obj.website, obj.email, obj.phone, obj.processbool)
  }
  
  def setDate(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, Some(newVal), obj.location, obj.price, obj.website, obj.email, obj.phone, obj.processbool)
  }
  
  def setLocation(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, Some(newVal), obj.price, obj.website, obj.email, obj.phone, obj.processbool)
  }
  
  def setPrice(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, obj.location, Some(newVal), obj.website, obj.email, obj.phone, obj.processbool)
  }
  
  def setWebsite(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, obj.location, obj.price, Some(newVal), obj.email, obj.phone, obj.processbool)
  }
  
  def setEmail(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, obj.location, obj.price, obj.website, Some(newVal), obj.phone, obj.processbool)
  }
  
  def setPhone(obj: Tables.PosterRow, newVal: String) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, obj.location, obj.price, obj.website, obj.email, Some(newVal), obj.processbool)
  }
  
  def setProcessBool(obj: Tables.PosterRow, newVal: Boolean) : Tables.PosterRow = {    
    return Tables.PosterRow(obj.id, obj.inputname, obj.uniquename, obj.text, obj.date, obj.location, obj.price, obj.website, obj.email, obj.phone, newVal)
  }   
    
}