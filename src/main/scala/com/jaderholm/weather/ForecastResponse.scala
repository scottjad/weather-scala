package com.jaderholm.weather

case class ForecastResponse(timezone: String,
                            currently: ForecastHourly,
                            hourly   : List[ForecastHourly],
                            daily    : List[ForecastDaily])
