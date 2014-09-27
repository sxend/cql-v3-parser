organization := "arimitsu.sf"

name := "cql-v3-parser"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "net.jpountz.lz4" % "lz4" % "1.2.0",
  "org.xerial.snappy" % "snappy-java" % "1.1.0.1"
)

publishTo := Some(Resolver.file("", file(Path.userHome.absolutePath + "/maven-repo"))(Patterns(true, Resolver.mavenStyleBasePattern)))

javacOptions ++= Seq("-source", "1.7")