package hedgehog.extra

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

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

  type NegativeLong = Long Refined Negative
  object NegativeLong    {
    final val MinValue: NegativeLong = refineMV[Negative](Long.MinValue)
    final val MaxValue: NegativeLong = -1L
  }
  type NonPositiveLong = Long Refined NonPositive
  object NonPositiveLong {
    final val MinValue: NonPositiveLong = refineMV[NonPositive](Long.MinValue)
    final val MaxValue: NonPositiveLong = 0L
  }
  type PositiveLong = Long Refined Positive
  object PositiveLong    {
    final val MinValue: PositiveLong = 1L
    final val MaxValue: PositiveLong = refineMV[Positive](Long.MaxValue)
  }
  type NonNegativeLong = Long Refined NonNegative
  object NonNegativeLong {
    final val MinValue: NonNegativeLong = 0L
    final val MaxValue: NonNegativeLong = refineMV[NonNegative](Long.MaxValue)
  }

}
