name := "Coursework"

organization := "com.pedrorijo91"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  guice,
  "com.pauldijou"     %% "jwt-play" % "3.1.0",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.typesafe.play" %% "play-json" % "2.7.3",
  "mysql" % "mysql-connector-java" % "8.0.15",
)