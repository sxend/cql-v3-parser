import bintray.Keys._

organization := "arimitsu.sf"

name := "cql-v3-parser"

version := "0.0.1-002"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.4", "2.11.2")

resolvers += "sxend repo releases" at "http://dl.bintray.com/sxend/releases"

resolvers += "sxend repo snapshots" at "http://dl.bintray.com/sxend/snapshots"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.2" % "test",
  "net.jpountz.lz4" % "lz4" % "1.2.0",
  "org.xerial.snappy" % "snappy-java" % "1.1.0.1"
)

publishMavenStyle := true

Seq(bintraySettings: _*)

repository in bintray := {
  if (version.value.matches("^[0-9]\\.[0-9]*\\.[0-9]*$")) "releases" else "snapshots"
}

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.8")
