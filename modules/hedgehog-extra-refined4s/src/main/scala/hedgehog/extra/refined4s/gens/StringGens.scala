package hedgehog.extra.refined4s.gens

import refined4s.types.all.*
import hedgehog.extra.Gens
import hedgehog.{Gen, Range}

/** @author Kevin Lee
  * @since 2021-04-07
  */
trait StringGens {

  def genNonWhitespaceString(maxLength: PosInt): Gen[NonEmptyString] =
    Gens
      .genUnsafeNonWhitespaceString(maxLength.value)
      .map(NonEmptyString.unsafeFrom)

  def genNonWhitespaceStringMinMax(minLength: PosInt, maxLength: PosInt): Gen[NonEmptyString] =
    Gens
      .genUnsafeNonWhitespaceStringMinMax(minLength.value, maxLength.value)
      .map(NonEmptyString.unsafeFrom)

  def genNonEmptyString(charGen: Gen[Char], maxLength: PosInt): Gen[NonEmptyString] =
    Gen.string(charGen, Range.linear(1, maxLength.value)).map(NonEmptyString.unsafeFrom)

  import scala.compiletime.{constValue, error}

  def genNonEmptyStringMinMax(charGen: Gen[Char], minLength: PosInt, maxLength: PosInt): Gen[NonEmptyString] =
    Gen.string(charGen, Range.linear(minLength.value, maxLength.value)).map(NonEmptyString.unsafeFrom)

  def genNonBlankString(maxLength: PosInt): Gen[NonBlankString] =
    Gens
      .genUnsafeNonWhitespaceString(maxLength.value)
      .map(NonBlankString.unsafeFrom)

  def genNonBlankStringMinMax(minLength: PosInt, maxLength: PosInt): Gen[NonBlankString] =
    Gens
      .genUnsafeNonWhitespaceStringMinMax(minLength.value, maxLength.value)
      .map(NonBlankString.unsafeFrom)

  def genUuid: Gen[Uuid] =
    Gen.constant(java.util.UUID.randomUUID).map(Uuid(_))

}
object StringGens extends StringGens
