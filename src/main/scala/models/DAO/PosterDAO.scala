package models.DAO

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

  def close() = db.close()

  def insert(newObj: Tables.PosterRow) {
    var query = poster += newObj
    var result = Await.result(db.run(query), Duration.Inf)
  }

  def update(newObj: Tables.PosterRow) = {
    val query = poster.filter { x => x.id === newObj.id }
    val updateAction = query.update(newObj)
    Await.result(db.run(updateAction), Duration.Inf)
  }

  def findAll(): Seq[Tables.PosterRow] = {
    var list = List[Tables.PosterRow]()
    val query = poster
    val result = Await.result(db.run(query.result), Duration.Inf)
    return result
  }

  def findByID(id: Int): Seq[Tables.PosterRow] = {
    var list = List[Tables.PosterRow]()
    val query = poster.filter { x => x.id === id }
    val result = Await.result(db.run(query.result), Duration.Inf)
    return result
  }

  def findByUniqueName(uniquename: String): Seq[Tables.PosterRow] = {
    var list = List[Tables.PosterRow]()
    val query = poster.filter { x => x.uniquename === uniquename }
    val result = Await.result(db.run(query.result), Duration.Inf)
    return result
  }

  def findByProcessBool(bool: Boolean): Seq[Tables.PosterRow] = {
    var list = List[Tables.PosterRow]()
    var query = poster.filter { x => x.processbool === bool }
    val result = Await.result(db.run(query.result), Duration.Inf)
    return result
  }  
}

