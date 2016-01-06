package models.DAO

import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.Play.current
import java.io.File
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._
import models.Tables
import models.Tables._
import models.Tables.profile.api._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class PosterDAO {

  val db = Database.forConfig("projet3A")
  val poster = TableQuery[Poster]
  
  def insert(id:Int, inputname: String, uniquename: String,  text: String, date: String, location: String, price: String, website: String, email: String, phone: String,processBool:Boolean){    
    var query = poster += Tables.PosterRow(id, Some(inputname), Some(uniquename), Some(text), Some(date), Some(location), Some(price), Some(website), Some(email), Some(phone), processBool)
    var result =  Await.result(db.run(query),Duration.Inf)
  
  }
  
  
  def findAll(): Seq[Tables.PosterRow] = {   
    var list = List[Tables.PosterRow]()
      val query = poster.map(p => (p.id, p.inputname, p.uniquename, p.text, p.date, p.location, p.price, p.website, p.email, p.phone,p.processbool))
      val result = Await.result(db.run(query.result).map {
        _.map { p => Tables.PosterRow(p._1, p._2, p._3, p._4, p._5, p._6, p._7, p._8, p._9, p._10,p._11) }
      },Duration.Inf)     
    return result
  }
  
  
  def findByID(id: Int): Seq[Tables.PosterRow] = {   
    var list = List[Tables.PosterRow]()
      val query = poster.filter { x => x.id===id }.map(p => (p.id, p.inputname,p.uniquename, p.text, p.date, p.location, p.price, p.website, p.email, p.phone, p.processbool))
      val result = Await.result(db.run(query.result).map {
        _.map { p => Tables.PosterRow(p._1, p._2, p._3, p._4, p._5, p._6, p._7, p._8, p._9, p._10, p._11) }
      }, Duration.Inf)
    return result
  }
  
  
  def findByUniqueName(uniquename: String): Seq[Tables.PosterRow] = {   
    var list = List[Tables.PosterRow]()
      val query = poster.filter { x => x.uniquename===uniquename }.map(p => (p.id, p.uniquename, p.inputname, p.text, p.date, p.location, p.price, p.website, p.email, p.phone, p.processbool))
      val result = Await.result(db.run(query.result).map {
        _.map { p => Tables.PosterRow(p._1, p._2, p._3, p._4, p._5, p._6, p._7, p._8, p._9, p._10, p._11) }
      }, Duration.Inf)
    return result
  }
    
}

