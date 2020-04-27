package com.jaderholm.weather

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._

object Weather {
  def main(args: Array[String]): Unit = {
    val apiKey = sys.env.get("DARKSKY_API_KEY")

    if (apiKey.isEmpty) {
      System.err.println("No API key found. DARKSKY_API_KEY environment variable must be set")
      sys.exit(1)
    }

    if (args.length < 4) {
      System.err.println("Usage: weather LATITUDE LONGITUDE HOURS SUBROUTINE")
      sys.exit(1)
    }
    val latitude = args(0)
    val longitude = args(1)
    val displayHoursCount = args(2).toInt
    val subRoutine = args(3)

    implicit val backend = HttpURLConnectionBackend()

    val url = uri"https://api.darksky.net/forecast/$apiKey/$latitude,$longitude"

    val request: Request[String, Nothing] =
      sttp.get(url)

    if (subRoutine == "normal")
      print("Sending request...")

    import Parsing._
    val response = request.response(asJson[ForecastResponse]).send()
    response.unsafeBody.map(response => subRoutine match {
      case "normal" => println(ConsoleForecast.report(response, displayHoursCount))
      case "goodMorning" => println(GoodMorning.report(response))
    })
  }
}
