package com.jaderholm.weather

import sttp.client._
import sttp.client.circe._

object Weather {

  def main(args: Array[String]): Unit = {
    val apiKey = sys.env.get("DARKSKY_API_KEY")

    if (apiKey.isEmpty) {
      System.err.println(
        "No API key found. DARKSKY_API_KEY environment variable must be set"
      )
      sys.exit(1)
    }

    if (args.length < 4) {
      System.err.println("Usage: weather LATITUDE LONGITUDE HOURS SUBROUTINE")
      sys.exit(1)
    }
    val latitude          = args(0)
    val longitude         = args(1)
    val displayHoursCount = args(2).toInt
    val subRoutine        = args(3)

    implicit val backend = HttpURLConnectionBackend()

    val url = uri"https://api.darksky.net/forecast/$apiKey/$latitude,$longitude"

    val request =
      basicRequest.get(url)

    if (subRoutine == "normal")
      print("Sending request...")

    import Parsing._
    val response = request.response(asJson[ForecastResponse]).send()
    response.body.map(
      response =>
        subRoutine match {
          case "normal" =>
            println(ConsoleForecast.report(response, displayHoursCount))
          case "goodMorning" => println(GoodMorning.report(response))
        }
    )
  }
}
