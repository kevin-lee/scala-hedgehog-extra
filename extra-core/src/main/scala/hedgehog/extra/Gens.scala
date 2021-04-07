package hedgehog.extra

import hedgehog._

/** @author Kevin Lee
  * @since 2021-04-06
  */
object Gens {

  def genCharByRange(range: List[(Int, Int)]): Gen[Char] =
    Gen.frequencyUnsafe(
      range.map {
        case (from, to) =>
          (to + 1 - from) -> Gen.char(from.toChar, to.toChar)
      }
    )

  def genNonWhitespaceChar: Gen[Char] =
    genCharByRange(common.NonWhitespaceCharRange)

}
