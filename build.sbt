name := "WriteSQLTest"

version := "0.1"

scalaVersion := "2.13.4"

val AkkaVersion = "2.5.31"

idePackagePrefix := Some("com.sqlwriter")

libraryDependencies ++= Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
  "com.typesafe" % "config" % "1.4.1",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion
)