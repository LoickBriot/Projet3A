package models

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Poster.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Poster
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param inputname Database column inputName SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param uniquename Database column uniqueName SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param text Database column text SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param location Database column location SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param price Database column price SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param website Database column website SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param email Database column email SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param phone Database column phone SqlType(VARCHAR), Length(100,true), Default(None)
   *  @param processbool Database column processBool SqlType(BIT) */
  case class PosterRow(id: Int, inputname: Option[String] = None, uniquename: Option[String] = None, text: Option[String] = None, date: Option[String] = None, location: Option[String] = None, price: Option[String] = None, website: Option[String] = None, email: Option[String] = None, phone: Option[String] = None, processbool: Boolean)
  /** GetResult implicit for fetching PosterRow objects using plain SQL queries */
  implicit def GetResultPosterRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Boolean]): GR[PosterRow] = GR{
    prs => import prs._
    PosterRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean]))
  }
  /** Table description of table poster. Objects of this class serve as prototypes for rows in queries. */
  class Poster(_tableTag: Tag) extends Table[PosterRow](_tableTag, "poster") {
    def * = (id, inputname, uniquename, text, date, location, price, website, email, phone, processbool) <> (PosterRow.tupled, PosterRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), inputname, uniquename, text, date, location, price, website, email, phone, Rep.Some(processbool)).shaped.<>({r=>import r._; _1.map(_=> PosterRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column inputName SqlType(VARCHAR), Length(100,true), Default(None) */
    val inputname: Rep[Option[String]] = column[Option[String]]("inputName", O.Length(100,varying=true), O.Default(None))
    /** Database column uniqueName SqlType(VARCHAR), Length(100,true), Default(None) */
    val uniquename: Rep[Option[String]] = column[Option[String]]("uniqueName", O.Length(100,varying=true), O.Default(None))
    /** Database column text SqlType(VARCHAR), Length(100,true), Default(None) */
    val text: Rep[Option[String]] = column[Option[String]]("text", O.Length(100,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(100,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(100,varying=true), O.Default(None))
    /** Database column location SqlType(VARCHAR), Length(100,true), Default(None) */
    val location: Rep[Option[String]] = column[Option[String]]("location", O.Length(100,varying=true), O.Default(None))
    /** Database column price SqlType(VARCHAR), Length(100,true), Default(None) */
    val price: Rep[Option[String]] = column[Option[String]]("price", O.Length(100,varying=true), O.Default(None))
    /** Database column website SqlType(VARCHAR), Length(100,true), Default(None) */
    val website: Rep[Option[String]] = column[Option[String]]("website", O.Length(100,varying=true), O.Default(None))
    /** Database column email SqlType(VARCHAR), Length(100,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(100,varying=true), O.Default(None))
    /** Database column phone SqlType(VARCHAR), Length(100,true), Default(None) */
    val phone: Rep[Option[String]] = column[Option[String]]("phone", O.Length(100,varying=true), O.Default(None))
    /** Database column processBool SqlType(BIT) */
    val processbool: Rep[Boolean] = column[Boolean]("processBool")
  }
  /** Collection-like TableQuery object for table Poster */
  lazy val Poster = new TableQuery(tag => new Poster(tag))
}
