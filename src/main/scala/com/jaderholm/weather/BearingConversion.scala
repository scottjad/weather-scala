package com.jaderholm.weather

object BearingConversion {
  def bearingToDirection(x: Int): String = {
    /*
   0-30 N
   30-60 NE
   60-120 E
   120-150 SE
   150-210 S
   210-240 SW
   240-300 W
   300-330 NW
   330-360 N
   */
    if (x >= 0 && (x < 30 || x >= 330))
      "N"
    else if (x < 60)
      "NE"
    else if (x < 120)
      "E"
    else if (x < 150)
      "SE"
    else if (x < 210)
      "S"
    else if (x < 240)
      "SW"
    else if (x < 300)
      "W"
    else if (x < 330)
      "NW"
    else
      throw new Exception("$x not in expected range (0-360)")
  }
}