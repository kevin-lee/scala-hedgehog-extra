package hedgehog.extra.refined

import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString
import hedgehog.extra.Gens
import hedgehog.{Gen, Range}

/** @author Kevin Lee
  * @since 2021-04-07
  */
trait StringGens {

  def genNonWhitespaceString(maxLength: PosInt): Gen[NonEmptyString] =
    Gens
      .genUnsafeNonWhitespaceString(maxLength)
      .map(NonEmptyString.unsafeFrom)

  def genNonWhitespaceStringMinMax(minLength: PosInt, maxLength: PosInt): Gen[NonEmptyString] =
    Gens
      .genUnsafeNonWhitespaceStringMinMax(minLength, maxLength)
      .map(NonEmptyString.unsafeFrom)

  def genNonEmptyString(charGen: Gen[Char], maxLength: PosInt): Gen[NonEmptyString] =
    Gen.string(charGen, Range.linear(1, maxLength.value)).map(NonEmptyString.unsafeFrom)

  def genNonEmptyStringMinMax(charGen: Gen[Char], minLength: PosInt, maxLength: PosInt): Gen[NonEmptyString] =
    Gen.string(charGen, Range.linear(minLength.value, maxLength.value)).map(NonEmptyString.unsafeFrom)

}
object StringGens extends StringGens
