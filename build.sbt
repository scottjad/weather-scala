name := "weather-scala"

version := "0.2"

scalaVersion := "2.12.6"

scalacOptions += "-deprecation"

// scalatest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "0.9.0"

// circe
val circeVersion = "0.8.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-optics"
).map(_ % circeVersion)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.softwaremill.sttp" %% "core"  % "1.0.5"
libraryDependencies += "com.softwaremill.sttp" %% "circe" % "1.0.5"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0" withSources() withJavadoc()
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.7.0"
mainClass in assembly := Some("com.jaderholm.weather.Weather")
