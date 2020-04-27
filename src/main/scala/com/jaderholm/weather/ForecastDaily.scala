package com.jaderholm.weather

import com.github.nscala_time.time.Imports.DateTime

case class ForecastDaily(time             : DateTime,
                         summary          : String,
                         windSpeed        : Float,
                         precipProbability: Float,
                         precipType       : Option[String],
                         temperatureHigh  : Float,
                         temperatureLow   : Float,
                         moonPhase        : Float,
                         sunriseTime      : DateTime,
                         sunsetTime       : DateTime)
  {
    def display(): String =
      "\n" +
        "%s High: %3.0f°F Low: %3.0f°F Sunrise: %s Sunset: %s Moonphase: %3.0f%%"
          .format(time.toString("yyy-MM-dd"),
          temperatureHigh,
          temperatureLow,
          sunriseTime.toString("hh:mm"),
          sunsetTime.toString("hh:mm"),
          moonPhase * 100) +
        "\n" + ("-" * 79)

  }
