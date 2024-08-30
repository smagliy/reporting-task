ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.19"

lazy val root = (project in file("."))
  .settings(
    name := "reporting-task",
    libraryDependencies ++= Seq(
      "org.apache.commons" % "commons-csv" % "1.10.0",
      "org.slf4j" % "slf4j-api" % "1.7.5",
      "org.slf4j" % "slf4j-simple" % "1.7.5"
    )
  )
