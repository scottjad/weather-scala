package com.jaderholm.weather

//import io.circe.generic.auto._
// import io.circe.optics.JsonPath._
import cats.syntax.either._
import com.github.nscala_time.time.Imports._
import io.circe.{Decoder, _}
import io.circe.generic.semiauto._

object Parsing
  {

    def unixEpochToDateTime(unixEpoch: Int, timezone: String): DateTime =
      new DateTime(unixEpoch * 1000L, DateTimeZone.forID(timezone))

    implicit val decodeForecastHourly: Decoder[ForecastHourly] = deriveDecoder[ForecastHourly]
    implicit val encodeForecastHourly: Encoder[ForecastHourly] = deriveEncoder[ForecastHourly]

    implicit val decodeForecastDaily: Decoder[ForecastDaily] = deriveDecoder[ForecastDaily]
    implicit val encodeForecastDaily: Encoder[ForecastDaily] = deriveEncoder[ForecastDaily]

    // We shadow this inside decodeForecastResponse, so this is never used.
    // If it's not here though, other Circe thinks we don't have a decoder for DateTime.
    implicit val decodeDateTime: Decoder[DateTime] =
    Decoder.decodeInt.emap { i =>
      val defaultTimezone = "America/Los_Angeles"
      Either
        .catchNonFatal(unixEpochToDateTime(i, defaultTimezone))
        .leftMap(t => "DateTime parsing error")
    }

    implicit val encodeDateTime: Encoder[DateTime] =
      Encoder.encodeInt.contramap[DateTime](_.getMillis.toInt / 1000)

    // Need a custom decoder 1) to handle intermediate "data" property and 2) to
    // get the timezone out of response before processing the child objects.
    implicit val decodeForecastResponse: Decoder[ForecastResponse] =
    Decoder.instance(c => {
      val tz = c.downField("timezone").as[String]

      tz match {
        case Left(msg) => throw new Exception("Couldn't find timezone in Response: $msg")
        case Right(tz) => {
          implicit val decodeDateTime: Decoder[DateTime] =
            Decoder.decodeInt.emap { i =>
              Either
                .catchNonFatal(unixEpochToDateTime(i, tz))
                .leftMap(t => "DateTime parsing error")
            }
          for {
            cur <- c.downField("currently").as[ForecastHourly]
            h <- c.downField("hourly").downField("data").as[List[ForecastHourly]]
            d <- c.downField("daily").downField("data").as[List[ForecastDaily]]
          } yield ForecastResponse(tz, cur, h, d)
        }
      }
    })

  }
