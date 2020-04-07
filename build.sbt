name := """black-forest"""
organization := "org.planingo"

version := "0.0-INIT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  javaJdbc ,
  ehcache ,
  javaWs,
  "org.choco-solver" % "choco-solver" % "4.10.2" withSources() withJavadoc())

libraryDependencies += guice
