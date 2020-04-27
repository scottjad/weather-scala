package com.jaderholm.weather

object ConsoleForecast {

  def report(response: ForecastResponse, displayHoursCount: Int): String = {
    val sb = new StringBuilder

    sb.append(Util.clearLine) // clear line

    val current     = response.currently
    val startDate   = current.time.getDayOfYear - 1
    var currentDate = startDate

    (current :: response.hourly.drop(1))
      .take(displayHoursCount)
      .foreach(hourly => {
        if (hourly.time.getDayOfYear != currentDate) {
          currentDate = hourly.time.getDayOfYear
          response.daily
            .find(daily => daily.time.getDayOfYear == currentDate)
            .map(
              forecastDaily =>
                sb.append(forecastDaily.displayWithBorder() + "\n")
            )
        }
        sb.append(hourly.display + "\n")
      })
    sb.append("\n")
    response.daily
      .drop(currentDate - startDate)
      .foreach(d => sb.append(d.display()))
    sb.toString
  }
}
