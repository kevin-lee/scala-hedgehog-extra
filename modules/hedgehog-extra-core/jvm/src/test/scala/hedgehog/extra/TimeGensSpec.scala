package hedgehog.extra

import hedgehog._
import hedgehog.runner._

import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDate, LocalTime, ZoneOffset}

/** @author Kevin Lee
  * @since 2024-02-21
  */
object TimeGensSpec extends Properties {

  override def tests: List[Test] = List(
    property("test TimeGens.genInstant(from, to)", testGenInstant),
    property("test TimeGens.genInstantFrom(baseInstant, durationAgo, durationAfter)", testGenInstantFrom),
    property("test TimeGens.genLocalDate(from, to)", testGenLocalDate),
    property("test TimeGens.genLocalDateFrom(baseLocalDate, durationAgo, durationAfter)", testGenLocalDateFrom)
  )

  def testGenInstant: Property =
    for {
      base   <- Gen.constant(Instant.now()).log("base")
      from   <- Gen.constant(base.minus(100, ChronoUnit.DAYS)).log("from")
      to     <- Gen.constant(base.plus(100, ChronoUnit.DAYS)).log("to")
      actual <- TimeGens.genInstant(from, to).log("actual")
    } yield Result
      .diffNamed("actual == from || actual.isAfter(from) || actual == to || actual.isBefore(to)", actual, (from, to)) {
        case (actual, (from, to)) =>
          actual == from || actual.isAfter(from) || actual == to || actual.isBefore(to)
      }
      .log(
        s"""actual=${actual.toString}
           |  from=${from.toString}
           |    to=${to.toString}
           |""".stripMargin
      )

  def testGenInstantFrom: Property = {
    import scala.concurrent.duration._

    for {
      base   <- Gen.constant(Instant.now()).log("base")
      from   <- Gen.int(Range.linear(0, 100)).map(_.days).log("from")
      to     <- Gen.int(Range.linear(0, 100)).map(_.days).log("to")
      actual <- TimeGens.genInstantFrom(base, from, to).log("actual")
    } yield {
      val fromExpected = base.minusMillis(from.toMillis)
      val toExpected   = base.plusMillis(to.toMillis)
      println(
        s"""===============================================================================
           |Test: TimeGens.genInstantFrom(${base.toString}, ${from.toString}, ${to.toString})
           |- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
           |        base=${base.toString}
           |             ${base.toEpochMilli} ms
           |             ${base.getEpochSecond} s
           |             ${base.getNano} ns
           |        from=${from.toString}
           |          to=${to.toString}
           |      actual=${actual.toString}
           |fromExpected=${fromExpected.toString}
           |  toExpected=${toExpected.toString}
           |-------------------------------------------------------------------------------
           |""".stripMargin
      )
      Result.all(
        List(
          Result.any(
            List(
              (actual ==== fromExpected).log(
                s"""actual ==== fromExpected failed
                 |      actual=${actual.toString}
                 |fromExpected=${fromExpected.toString}
                 |  toExpected=${toExpected.toString}
                 |""".stripMargin
              ),
              Result
                .assert(actual.isAfter(fromExpected))
                .log(
                  s"""actual.isAfter(fromExpected) failed
                   |      actual=${actual.toString}
                   |fromExpected=${fromExpected.toString}
                   |  toExpected=${toExpected.toString}
                   |""".stripMargin
                )
            )
          ),
          Result.any(
            List(
              (actual ==== toExpected).log(
                s"""actual ==== toExpected failed
               |      actual=${actual.toString}
               |fromExpected=${fromExpected.toString}
               |  toExpected=${toExpected.toString}
               |""".stripMargin
              ),
              Result
                .assert(actual.isBefore(toExpected))
                .log(
                  s"""actual.isBefore(toExpected) failed
               |      actual=${actual.toString}
               |fromExpected=${fromExpected.toString}
               |  toExpected=${toExpected.toString}
               |""".stripMargin
                )
            )
          )
        )
      )
    }
  }

  def testGenLocalDate: Property =
    for {
      base   <- Gen.constant(LocalDate.now()).log("base")
      from   <- Gen.constant(base.minus(100, ChronoUnit.DAYS)).log("from")
      to     <- Gen.constant(base.plus(100, ChronoUnit.DAYS)).log("to")
      actual <- TimeGens.genLocalDate(from, to).log("actual")
    } yield {
      println(
        s"""=====================================================
           |Test: TimeGens.genLocalDate(${from.toString}, ${to.toString})
           |- - - - - - - - - - - - - - - - - - - - - - - - - - -
           |        base=${base.toString}
           |             ${base.toEpochDay} days
           |             ${base.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC)} s (midnight, UTC)
           |        from=${from.toString}
           |          to=${to.toString}
           |      actual=${actual.toString}
           |-----------------------------------------------------
           |""".stripMargin
      )
      Result
        .diffNamed(
          "actual == from || actual.isAfter(from) || actual == to || actual.isBefore(to)",
          actual,
          (from, to)
        ) {
          case (actual, (from, to)) =>
            actual == from || actual.isAfter(from) || actual == to || actual.isBefore(to)
        }
        .log(
          s"""actual=${actual.toString}
             |  from=${from.toString}
             |    to=${to.toString}
             |""".stripMargin
        )
    }

  def testGenLocalDateFrom: Property = {
    import scala.concurrent.duration._
    for {
      base   <- Gen.constant(LocalDate.now()).log("base")
      from   <- Gen.int(Range.linear(0, 100)).map(_.days).log("from")
      to     <- Gen.int(Range.linear(0, 100)).map(_.days).log("to")
      actual <- TimeGens.genLocalDateFrom(base, from, to).log("actual")
    } yield {
      val fromExpected = base.minus(from.toDays, ChronoUnit.DAYS)
      val toExpected   = base.plus(to.toDays, ChronoUnit.DAYS)

      println(
        s"""===============================================================
           |Test: TimeGens.genLocalDateFrom(${base.toString}, ${from.toString}, ${to.toString})
           |- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
           |        base=${base.toString}
           |             ${base.toEpochDay} days
           |             ${base.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC)} s (midnight, UTC)
           |        from=${from.toString}
           |          to=${to.toString}
           |      actual=${actual.toString}
           |fromExpected=${fromExpected.toString}
           |  toExpected=${toExpected.toString}
           |---------------------------------------------------------------
           |""".stripMargin
      )
      Result.all(
        List(
          Result.any(
            List(
              (actual ==== fromExpected).log(
                s"""actual ==== fromExpected failed
                   |      actual=${actual.toString}
                   |fromExpected=${fromExpected.toString}
                   |  toExpected=${toExpected.toString}
                   |""".stripMargin
              ),
              Result
                .assert(actual.isAfter(fromExpected))
                .log(
                  s"""actual.isAfter(fromExpected) failed
                     |      actual=${actual.toString}
                     |fromExpected=${fromExpected.toString}
                     |  toExpected=${toExpected.toString}
                     |""".stripMargin
                )
            )
          ),
          Result.any(
            List(
              (actual ==== toExpected).log(
                s"""actual ==== toExpected failed
                   |      actual=${actual.toString}
                   |fromExpected=${fromExpected.toString}
                   |  toExpected=${toExpected.toString}
                   |""".stripMargin
              ),
              Result
                .assert(actual.isBefore(toExpected))
                .log(
                  s"""actual.isBefore(toExpected) failed
                     |      actual=${actual.toString}
                     |fromExpected=${fromExpected.toString}
                     |  toExpected=${toExpected.toString}
                     |""".stripMargin
                )
            )
          )
        )
      )
    }
  }

}
