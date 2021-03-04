package hedgehog.extra.refined

import eu.timepit.refined._
import eu.timepit.refined.numeric._

import hedgehog._

/** @author Kevin Lee
  * @since 2021-01-02
  */
object NumGens {

  def genNegativeInt(min: NegativeInt): Gen[NegativeInt] =
    Gen.int(Range.linear(min.value, -1)).map(refineV[Negative].unsafeFrom(_))

  def genNonPositiveInt(min: NonPositiveInt): Gen[NonPositiveInt] =
    Gen.int(Range.linear(min.value, 0)).map(refineV[NonPositive].unsafeFrom(_))

  def genPositiveInt(max: PositiveInt): Gen[PositiveInt] =
    Gen.int(Range.linear(1, max.value)).map(refineV[Positive].unsafeFrom(_))

  def genNonNegativeInt(max: NonNegativeInt): Gen[NonNegativeInt] =
    Gen.int(Range.linear(0, max.value)).map(refineV[NonNegative].unsafeFrom(_))

  def genNegativeLong(min: NegativeLong): Gen[NegativeLong] =
    Gen.long(Range.linear(min.value, -1L)).map(refineV[Negative].unsafeFrom(_))

  def genNonPositiveLong(min: NonPositiveLong): Gen[NonPositiveLong] =
    Gen.long(Range.linear(min.value, 0L)).map(refineV[NonPositive].unsafeFrom(_))

  def genPositiveLong(max: PositiveLong): Gen[PositiveLong] =
    Gen.long(Range.linear(1L, max.value)).map(refineV[Positive].unsafeFrom(_))

  def genNonNegativeLong(max: NonNegativeLong): Gen[NonNegativeLong] =
    Gen.long(Range.linear(0L, max.value)).map(refineV[NonNegative].unsafeFrom(_))

}
