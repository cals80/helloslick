name := """helloslick"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest"       %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.slick"  %% "slick" % "3.1.1",
  "com.typesafe.slick"  %% "slick-codegen" % "3.1.1",
  "com.typesafe.slick"  %% "slick-hikaricp" % "3.1.1",
  "org.slf4j"           % "slf4j-nop" % "1.7.10",
  "mysql"               % "mysql-connector-java"  % "5.1.38"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"


slick <<= slickCodeGenTask

//sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = new File("").getAbsolutePath + "/src/main/scala/"
  val username = "root"
  val password = ""
  val url = "jdbc:mysql://localhost/education_backend"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "com.example"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/com/example/Tables.scala"
  Seq(file(fname))
}