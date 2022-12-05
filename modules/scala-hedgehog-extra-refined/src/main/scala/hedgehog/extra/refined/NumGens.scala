package hedgehog.extra.refined

import eu.timepit.refined.types.numeric._
import hedgehog._

/** @author Kevin Lee
  * @since 2021-01-02
  */
object NumGens {

  def genNegInt(min: NegInt, max: NegInt): Gen[NegInt] =
    Gen.int(Range.linear(min.value, max.value)).map(NegInt.unsafeFrom)

  def genNegInt(min: NegInt): Gen[NegInt] =
    genNegInt(min, NegInt.MaxValue)

  def genNonPosInt(min: NonPosInt, max: NonPosInt): Gen[NonPosInt] =
    Gen.int(Range.linear(min.value, max.value)).map(NonPosInt.unsafeFrom)

  def genNonPosInt(min: NonPosInt): Gen[NonPosInt] =
    genNonPosInt(min, NonPosInt.MaxValue)

  def genPosInt(min: PosInt, max: PosInt): Gen[PosInt] =
    Gen.int(Range.linear(min.value, max.value)).map(PosInt.unsafeFrom)

  def genPosInt(max: PosInt): Gen[PosInt] =
    genPosInt(PosInt.MinValue, max)

  def genNonNegInt(min: NonNegInt, max: NonNegInt): Gen[NonNegInt] =
    Gen.int(Range.linear(min.value, max.value)).map(NonNegInt.unsafeFrom)

  def genNonNegInt(max: NonNegInt): Gen[NonNegInt] =
    genNonNegInt(NonNegInt.MinValue, max)

  def genNegLong(min: NegLong, max: NegLong): Gen[NegLong] =
    Gen.long(Range.linear(min.value, max.value)).map(NegLong.unsafeFrom)

  def genNegLong(min: NegLong): Gen[NegLong] =
    genNegLong(min, NegLong.MaxValue)

  def genNonPosLong(min: NonPosLong, max: NonPosLong): Gen[NonPosLong] =
    Gen.long(Range.linear(min.value, max.value)).map(NonPosLong.unsafeFrom)

  def genNonPosLong(min: NonPosLong): Gen[NonPosLong] =
    genNonPosLong(min, NonPosLong.MaxValue)

  def genPosLong(min: PosLong, max: PosLong): Gen[PosLong] =
    Gen.long(Range.linear(min.value, max.value)).map(PosLong.unsafeFrom)

  def genPosLong(max: PosLong): Gen[PosLong] =
    genPosLong(PosLong.MinValue, max)

  def genNonNegLong(min: NonNegLong, max: NonNegLong): Gen[NonNegLong] =
    Gen.long(Range.linear(min.value, max.value)).map(NonNegLong.unsafeFrom)

  def genNonNegLong(max: NonNegLong): Gen[NonNegLong] =
    genNonNegLong(NonNegLong.MinValue, max)

  def genNegDouble(min: NegDouble, max: NegDouble): Gen[NegDouble] =
    Gen.double(Range.linearFrac(min.value, max.value)).map(NegDouble.unsafeFrom)

  def genNegDouble(min: NegDouble): Gen[NegDouble] =
    genNegDouble(min, NegDouble.MaxValue)

  def genNonPosDouble(min: NonPosDouble, max: NonPosDouble): Gen[NonPosDouble] =
    Gen.double(Range.linearFrac(min.value, max.value)).map(NonPosDouble.unsafeFrom)

  def genNonPosDouble(min: NonPosDouble): Gen[NonPosDouble] =
    genNonPosDouble(min, NonPosDouble.MaxValue)

  def genPosDouble(min: PosDouble, max: PosDouble): Gen[PosDouble] =
    Gen.double(Range.linearFrac(min.value, max.value)).map(PosDouble.unsafeFrom)

  def genPosDouble(max: PosDouble): Gen[PosDouble] =
    genPosDouble(PosDouble.MinValue, max)

  def genNonNegDouble(min: NonNegDouble, max: NonNegDouble): Gen[NonNegDouble] =
    Gen.double(Range.linearFrac(min.value, max.value)).map(NonNegDouble.unsafeFrom)

  def genNonNegDouble(max: NonNegDouble): Gen[NonNegDouble] =
    genNonNegDouble(NonNegDouble.MinValue, max)

}
