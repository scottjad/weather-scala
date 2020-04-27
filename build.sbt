name := "weather-scala"

version := "0.3"

scalaVersion := "2.13.1"

scalacOptions += "-deprecation"

// scalatest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

// for cats
// scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.0"

// circe
val circeVersion = "0.13.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-optics"
).map(_ % circeVersion)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "com.softwaremill.sttp.client" %% "core"  % "2.1.0-RC1"
libraryDependencies += "com.softwaremill.sttp.client" %% "circe" % "2.1.0-RC1"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.24.0" withSources () withJavadoc ()
libraryDependencies += "com.lihaoyi"            %% "scalatags"   % "0.9.0"
mainClass in assembly := Some("com.jaderholm.weather.Weather")
