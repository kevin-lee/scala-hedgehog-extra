package hedgehog.extra

import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import hedgehog.Result

/** @author Kevin Lee
  * @since 2021-01-02
  */
package object refined {

  type NegativeInt = Int Refined Negative
  object NegativeInt    {
    final val MinValue: NegativeInt = refineMV[Negative](Int.MinValue)
    final val MaxValue: NegativeInt = -1
  }
  type NonPositiveInt = Int Refined NonPositive
  object NonPositiveInt {
    final val MinValue: NonPositiveInt = refineMV[NonPositive](Int.MinValue)
    final val MaxValue: NonPositiveInt = 0
  }
  type PositiveInt = Int Refined Positive
  object PositiveInt    {
    final val MinValue: PositiveInt = 1
    final val MaxValue: PositiveInt = refineMV[Positive](Int.MaxValue)
  }
  type NonNegativeInt = Int Refined NonNegative
  object NonNegativeInt {
    final val MinValue: NonNegativeInt = 0
    final val MaxValue: NonNegativeInt = refineMV[NonNegative](Int.MaxValue)
  }

  implicit final class PropertyOps[A](val a: A) extends AnyVal {
    def matchPattern(right: PartialFunction[Any, _]): Result =
      if (right.isDefinedAt(a))
        Result.success
      else
        Result.failure.log(s"actual: $a")
  }
}
