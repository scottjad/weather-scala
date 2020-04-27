package com.jaderholm.weather

object GoodMorning {
  def report(response: ForecastResponse): String = {
    val current = response.currently
    val sb = new StringBuilder()

    sb.append(
      s"""It's ${current.time.toString("h:mm aa")}.
         |The weather in Moses lake is ${current.summary}, ${current.temperature.toInt} degrees, with ${current.windSpeed.toInt} mph wind, and a uv index of ${current.uvIndex.toInt}.""".stripMargin)

    val currentDay = current.time.getDayOfYear
    val hourly = (current :: response.hourly.drop(1)) .takeWhile(p => p.time.getDayOfYear == currentDay)
    val maxTemp = hourly.maxBy(_.temperature)
    sb.append(s"""The high today will be ${maxTemp.temperature.toInt} degrees at ${maxTemp.time.toString("h aa")}.""")

    val sixOClock = hourly.filter(p => p.time.getHourOfDay == 18).headOption
    sixOClock foreach { p =>
      sb.append(s"At 6 pm it will be ${p.summary}, ${p.temperature.toInt} degrees, with ${p.windSpeed.toInt} mph wind, with gusts up to ${p.windGust.toInt} mph.")}

    response.daily.headOption foreach {
      d => sb.append(s"Sunset today will be at ${d.sunsetTime.toString("h:mm aa")}.")
    }
    //
    //      val differentSummaries = Util.partitionBy(hourly)(h => h.summary).map(_.head)
    //
    //      val likelyPrecipHours = differentSummaries.filter(p => p.head.precipProbability >= 0.3)
    //
    //      for ( hour <- likelyPrecipHours) {
    //        println(f"There's a ${hour.precipProbability * 100}%.0f%% chance of ${hour.precipType.get} at ${hour.time.toString("h:mm aa")}.")
    //      }
    sb.toString
  }

}