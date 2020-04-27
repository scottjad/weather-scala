package com.jaderholm.weather

object ConsoleForecast {

  def report(response: ForecastResponse, displayHoursCount: Int): String = {
    val sb = new StringBuilder

    sb.append(Util.clearLine) // clear line

    val current = response.currently

    var currentDate = current.time.getDayOfMonth - 1

    (current :: response.hourly.drop(1))
      .take(displayHoursCount)
      .foreach(hourly => {
        if (hourly.time.getDayOfMonth != currentDate) {
          currentDate = hourly.time.getDayOfMonth
          response.daily
            .find(daily => daily.time.getDayOfMonth == currentDate)
            .map(forecastDaily => sb.append(forecastDaily.display() + "\n"))
        }
        sb.append(hourly.display + "\n")
      })
    sb.toString
  }
}
