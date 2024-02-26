package hedgehog.extra

import hedgehog._
import hedgehog.runner._

import java.time.Instant
import java.time.temporal.ChronoUnit

/** @author Kevin Lee
  * @since 2024-02-21
  */
object TimeGensSpec extends Properties {

  override def tests: List[Test] = List(
    property("test TimeGens.genInstant(from, to)", testGenInstant),
    property("test TimeGens.genInstantFrom(from, to)", testGenInstantFrom)
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
        s"""        base=${base.toString}
           |             ${base.toEpochMilli} ms
           |             ${base.getEpochSecond} s
           |             ${base.getNano} ns
           |        from=${from.toString}
           |          to=${to.toString}
           |      actual=${actual.toString}
           |fromExpected=${fromExpected.toString}
           |  toExpected=${toExpected.toString}
           |--------------------------------------
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
