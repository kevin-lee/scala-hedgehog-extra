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
    property("test TimeGens.genInstant(from, to)", testGenInstant)
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
}
