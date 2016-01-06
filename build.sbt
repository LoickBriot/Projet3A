name := """Projet3A"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.zaxxer" % "HikariCP" % "2.3.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.elasticsearch" % "elasticsearch" % "1.7.1",
  "com.lihaoyi" % "upickle_2.11" % "0.3.6",
  "com.google.re2j" % "re2j" % "1.1",
  "org.apache.logging.log4j" % "log4j" % "2.5",
  "org.webjars.npm" % "foundation-sites" % "6.0.3"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

disablePlugins(PlayLayoutPlugin)

fork in run := true


/*
import java.io.File
val rootPath: String = System.getProperties().get("user.dir").toString()

slick <<= slickCodeGenTask
sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = rootPath + File.separator + "src" + File.separator + "main" + File.separator + "scala" + File.separator
  val username = "root"
  val password = ""
  val url = "jdbc:mysql://localhost/festalorproject"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "models" + File.separator  +"Tables.scala"
  Seq(file(fname))
}*/

