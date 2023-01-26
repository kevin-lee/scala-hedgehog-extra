package hedgehog.extra.refined

import hedgehog._
import eu.timepit.refined.types.time._

/** @author Kevin Lee
  * @since 2023-01-26
  */
trait TimeGens {
  def genMonthMinMax(min: Month, max: Month): Gen[Month] =
    Gen.int(Range.linear(min.value, max.value)).map(Month.unsafeFrom)

  def genMonth: Gen[Month] = genMonthMinMax(Month(1), Month(12))

  def genDayMinMax(min: Day, max: Day): Gen[Day] =
    Gen.int(Range.linear(min.value, max.value)).map(Day.unsafeFrom)

  def genDay: Gen[Day] = genDayMinMax(Day(1), Day(31))

  def genHourMinMax(min: Hour, max: Hour): Gen[Hour] =
    Gen.int(Range.linear(min.value, max.value)).map(Hour.unsafeFrom)

  def genHour: Gen[Hour] =
    genHourMinMax(Hour(0), Hour(23))

  def genMinuteMinMax(min: Minute, max: Minute): Gen[Minute] =
    Gen.int(Range.linear(min.value, max.value)).map(Minute.unsafeFrom)

  def genMinute: Gen[Minute] =
    genMinuteMinMax(Minute(0), Minute(59))

  def genSecondMinMax(min: Second, max: Second): Gen[Second] =
    Gen.int(Range.linear(min.value, max.value)).map(Second.unsafeFrom)

  def genSecond: Gen[Second] =
    genSecondMinMax(Second(0), Second(59))

  def genMillisMinMax(min: Millis, max: Millis): Gen[Millis] =
    Gen.int(Range.linear(min.value, max.value)).map(Millis.unsafeFrom)

  def genMillis: Gen[Millis] =
    genMillisMinMax(Millis(0), Millis(999))

}
object TimeGens extends TimeGens
