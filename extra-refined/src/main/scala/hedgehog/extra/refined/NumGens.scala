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

}
