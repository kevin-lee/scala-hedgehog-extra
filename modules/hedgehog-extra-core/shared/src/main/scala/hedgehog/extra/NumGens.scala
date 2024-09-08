package hedgehog.extra

import hedgehog._

/** @author Kevin Lee
  * @since 2023-01-26
  */
trait NumGens {
  def genIntMinMaxPair(min: Int, max: Int): Gen[(Int, Int)] =
    for {
      mn <- Gen.int(Range.linear(min, max))
      mx <- Gen.int(Range.linear(min, max))
    } yield if (mn < mx) (mn, mx) else (mx, mn)

  def genLongMinMaxPair(min: Long, max: Long): Gen[(Long, Long)] =
    for {
      mn <- Gen.long(Range.linear(min, max))
      mx <- Gen.long(Range.linear(min, max))
    } yield if (mn < mx) (mn, mx) else (mx, mn)

}
object NumGens extends NumGens
