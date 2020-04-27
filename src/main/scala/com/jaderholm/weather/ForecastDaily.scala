package com.jaderholm.weather

import com.github.nscala_time.time.Imports.DateTime

case class ForecastDaily(
    time: DateTime,
    summary: String,
    windSpeed: Float,
    windGust: Float,
    windBearing: Float,
    windGustTime: DateTime,
    precipProbability: Float,
    precipType: Option[String],
    temperatureHigh: Float,
    temperatureLow: Float,
    moonPhase: Float,
    sunriseTime: DateTime,
    sunsetTime: DateTime
) {

  def display(): String =
    "\n" +
      "%s High: %3.0f°F Low: %3.0f°F Wind: %2.0f/%2.0f @ %s %3s Sunrise: %s Sunset: %s Moon: %3.0f%%"
        .format(
          time.toString("E MM-dd"),
          temperatureHigh,
          temperatureLow,
          windSpeed,
          windGust,
          windGustTime.toString("hh:mma"),
          BearingConversion.bearingToDirection(windBearing.toInt),
          sunriseTime.toString("hh:mm"),
          sunsetTime.toString("hh:mm"),
          moonPhase * 100
        )

  def displayWithBorder() =
    display + "\n" + ("-" * 98)

}
