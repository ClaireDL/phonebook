ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

// Claire stinks

lazy val root = (project in file("."))
  .settings(
    name := "Phonebook",
    exportJars := true,
    libraryDependencies += "com.colofabrix.scala" %% "figlet4s-core" % "0.3.0",
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
