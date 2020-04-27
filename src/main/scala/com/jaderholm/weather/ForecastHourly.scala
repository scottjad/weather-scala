package com.jaderholm.weather

import com.github.nscala_time.time.Imports.DateTime

case class ForecastHourly(time: DateTime,
                          summary: String,
                          windSpeed        : Float,
                          windGust         : Float,
                          windBearing      : Float,
                          precipProbability: Float,
                          precipType: Option[String],
                          pressure: Float,
                          uvIndex          : Float,
                          temperature      : Float)
  {
    def display(): String = {
      val precip =
        if (precipProbability == 0 || precipProbability <= 2)
          ""
        else
          "%3.0f%% chance of %s".format(precipProbability * 100, precipType.getOrElse(""))

      val wind = "%3.0f/%.0f mph".format(windSpeed, windGust)
      //    "│ %s │ %3.0f°F │ %3.0f mph, %3.0f, %3s │ %-20s %3.0f bar, %3.0f uv │ %20s │"
      "%s\t%3.0f°F\t%3.0f uv\t%10s\t%3s\t%-15s\t%20s"
        .format(time.toString("hh:mm"),
          temperature,
          uvIndex,
          wind,
          BearingConversion.bearingToDirection(windBearing.toInt),
          summary,
          precip)
    }
  }
