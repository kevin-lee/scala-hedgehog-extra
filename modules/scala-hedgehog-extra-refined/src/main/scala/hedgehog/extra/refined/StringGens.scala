package hedgehog.extra.refined

import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.types.string.NonEmptyString
import hedgehog.Gen
import hedgehog.extra.Gens

/** @author Kevin Lee
  * @since 2021-04-07
  */
object StringGens {

  def genNonWhitespaceString(maxLength: PositiveInt): Gen[NonEmptyString] =
    Gens
      .genNonWhitespaceStringUnsafe(maxLength)
      .map(str => refineV[NonEmpty].unsafeFrom(str))

}
