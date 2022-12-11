package hedgehog.extra.refined

import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString
import hedgehog.Gen
import hedgehog.extra.Gens

/** @author Kevin Lee
  * @since 2021-04-07
  */
object StringGens {

  def genNonWhitespaceString(maxLength: PosInt): Gen[NonEmptyString] =
    Gens
      .genUnsafeNonWhitespaceString(maxLength)
      .map(NonEmptyString.unsafeFrom)

}
