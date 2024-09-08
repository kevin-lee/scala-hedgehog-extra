package hedgehog.extra

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-01-26
  */
object NumGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("test NumGens.genIntMinMaxPair", testGenIntMinMaxPair),
    property("test NumGens.genLongMinMaxPair", testGenLongMinMaxPair)
  )

  def testGenIntMinMaxPair: Property =
    for {
      minAndMax <- NumGens.genIntMinMaxPair(Int.MinValue, Int.MaxValue).log("(min, max)")
      (min, max) = minAndMax
    } yield Result.diffNamed("(min, max) should be min <= max", min, max)(_ <= _)

  def testGenLongMinMaxPair: Property =
    for {
      minAndMax <- NumGens.genLongMinMaxPair(Long.MinValue, Long.MaxValue).log("(min, max)")
      (min, max) = minAndMax
    } yield Result.diffNamed("(min, max) should be min <= max", min, max)(_ <= _)

}
