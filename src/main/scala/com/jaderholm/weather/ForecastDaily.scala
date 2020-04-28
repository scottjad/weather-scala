package com.jaderholm.weather

import com.github.nscala_time.time.Imports.DateTime

case class ForecastDaily(
    time: DateTime,
    summary: String,
    icon: String,
    windSpeed: Float,
    windGust: Float,
    windBearing: Float,
    windGustTime: DateTime,
    precipProbability: Float,
    precipType: Option[String],
    precipAccumulation: Option[Float],
    precipIntensityMax: Option[Float],
    precipIntensityMaxTime: DateTime,
    temperatureHigh: Float,
    temperatureLow: Float,
    temperatureHighTime: DateTime,
    cloudCover: Float,
    visibility: Float,
    moonPhase: Float,
    sunriseTime: DateTime,
    sunsetTime: DateTime
) {

  private def maybeShowPrecip: String = {
    // FIXME neither precipAccumulation or precipIntensityMax appear to be getting values
    val accumulation = precipAccumulation match {
      case None        => ""
      case Some(value) => "%3.0f in".format(value)
    }

    val intensity = precipIntensityMax match {
      case None        => ""
      case Some(value) => "%3.0f in".format(value)
    }

    if (precipProbability > 0.3) {
      return "%3.0f%% %s (%s) @ %s".format(
        precipProbability * 100,
        accumulation,
        intensity,
        precipIntensityMaxTime.toString("kk:mm")
      )
    }
    else
      return ""
  }

  private def summarySection: String = {
    val sum = IconConversion.convert(icon)
    return (if (sum == "Partly Cloudy" || sum == "Mostly Cloudy") "Cloudy"
            else sum) + maybeShowPrecip
  }

  def display(): String =
    "\n" +
      "%s H: %3.0f°F @ %s L: %3.0f°F W: %2.0f/%2.0f @ %s %3s Rise: %s Set: %s Mn: %3.0f%% %-6s %3.0f%%"
        .format(
          time.toString("E MM-dd"),
          temperatureHigh,
          temperatureHighTime.toString("hh:mm"),
          temperatureLow,
          windSpeed,
          windGust,
          windGustTime.toString("kk:mm"),
          BearingConversion.bearingToDirection(windBearing.toInt),
          sunriseTime.toString("hh:mm"),
          sunsetTime.toString("hh:mm"),
          moonPhase * 100,
          summarySection,
          cloudCover * 100
        )

  def displayWithBorder() =
    display + "\n" + ("-" * 96)

}
