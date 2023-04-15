package hedgehog.extra.refined

import eu.timepit.refined.types.time._
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-01-26
  */
object TimeGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("test TimeGens.genMonthMinMaxOneValue", testGenMonthMinMaxOneValue),
    property("test TimeGens.genMonthMinMax", testGenMonthMinMax),
    property("test TimeGens.genMonth", testGenMonth),
    property("test TimeGens.genDayMinMaxOneValue", testGenDayMinMaxOneValue),
    property("test TimeGens.genDayMinMax", testGenDayMinMax),
    property("test TimeGens.genDay", testGenDay),
    property("test TimeGens.genHourMinMaxOneValue", testGenHourMinMaxOneValue),
    property("test TimeGens.genHourMinMax", testGenHourMinMax),
    property("test TimeGens.genHour", testGenHour),
    property("test TimeGens.genMinuteMinMaxOneValue", testGenMinuteMinMaxOneValue),
    property("test TimeGens.genMinuteMinMax", testGenMinuteMinMax),
    property("test TimeGens.genMinute", testGenMinute),
    property("test TimeGens.genSecondMinMaxOneValue", testGenSecondMinMaxOneValue),
    property("test TimeGens.genSecondMinMax", testGenSecondMinMax),
    property("test TimeGens.genSecond", testGenSecond),
    property("test TimeGens.genMillisMinMaxOneValue", testGenMillisMinMaxOneValue),
    property("test TimeGens.genMillisMinMax", testGenMillisMinMax),
    property("test TimeGens.genMillis", testGenMillis)
  )

  def testGenMonthMinMaxOneValue: Property =
    for {
      month  <- Gen.int(Range.linear(1, 12)).map(Month.unsafeFrom).log("month")
      actual <- TimeGens.genMonthMinMax(month, month).log("month")
    } yield actual ==== actual

  def testGenMonthMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(1, 12)
                     .map {
                       case (min, max) => (Month.unsafeFrom(min), Month.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genMonthMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenMonth: Property =
    for {
      actual <- TimeGens.genMonth.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 1", actual, Month.unsafeFrom(1))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 1"),
        Result
          .diffNamed(s"actual should be <= 12", actual, Month.unsafeFrom(12))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 12")
      )
    )

  /////

  def testGenDayMinMaxOneValue: Property =
    for {
      day    <- Gen.int(Range.linear(1, 31)).map(Day.unsafeFrom).log("day")
      actual <- TimeGens.genDayMinMax(day, day).log("actual")
    } yield actual ==== day

  def testGenDayMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(1, 12)
                     .map {
                       case (min, max) => (Day.unsafeFrom(min), Day.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genDayMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenDay: Property =
    for {
      actual <- TimeGens.genDay.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 1", actual, Day.unsafeFrom(1))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 1"),
        Result
          .diffNamed(s"actual should be <= 31", actual, Day.unsafeFrom(31))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 31")
      )
    )

  /////

  def testGenHourMinMaxOneValue: Property =
    for {
      hour   <- Gen.int(Range.linear(0, 23)).map(Hour.unsafeFrom).log("hour")
      actual <- TimeGens.genHourMinMax(hour, hour).log("actual")
    } yield actual ==== hour

  def testGenHourMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(0, 23)
                     .map {
                       case (min, max) => (Hour.unsafeFrom(min), Hour.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genHourMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenHour: Property =
    for {
      actual <- TimeGens.genHour.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 0", actual, Hour.unsafeFrom(0))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 0"),
        Result
          .diffNamed(s"actual should be <= 23", actual, Hour.unsafeFrom(23))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 23")
      )
    )

  /////

  def testGenMinuteMinMaxOneValue: Property =
    for {
      minute <- Gen.int(Range.linear(0, 59)).map(Minute.unsafeFrom).log("minute")
      actual <- TimeGens.genMinuteMinMax(minute, minute).log("actual")
    } yield actual ==== minute

  def testGenMinuteMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(0, 59)
                     .map {
                       case (min, max) => (Minute.unsafeFrom(min), Minute.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genMinuteMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenMinute: Property =
    for {
      actual <- TimeGens.genMinute.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 0", actual, Minute.unsafeFrom(0))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 0"),
        Result
          .diffNamed(s"actual should be <= 59", actual, Minute.unsafeFrom(59))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 59")
      )
    )

  /////

  def testGenSecondMinMaxOneValue: Property =
    for {
      second <- Gen.int(Range.linear(0, 59)).map(Second.unsafeFrom).log("second")
      actual <- TimeGens.genSecondMinMax(second, second).log("actual")
    } yield actual ==== second

  def testGenSecondMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(0, 59)
                     .map {
                       case (min, max) => (Second.unsafeFrom(min), Second.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genSecondMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenSecond: Property =
    for {
      actual <- TimeGens.genSecond.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 0", actual, Second.unsafeFrom(0))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 0"),
        Result
          .diffNamed(s"actual should be <= 59", actual, Second.unsafeFrom(59))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 59")
      )
    )

  /////

  def testGenMillisMinMaxOneValue: Property =
    for {
      millis <- Gen.int(Range.linear(0, 999)).map(Millis.unsafeFrom).log("millis")
      actual <- TimeGens.genMillisMinMax(millis, millis).log("actual")
    } yield actual ==== millis

  def testGenMillisMinMax: Property =
    for {
      minAndMax <- extra
                     .NumGens
                     .genIntMinMaxPair(0, 999)
                     .map {
                       case (min, max) => (Millis.unsafeFrom(min), Millis.unsafeFrom(max))
                     }
                     .log("(min, max)")
      (min, max) = minAndMax
      actual <- TimeGens.genMillisMinMax(min, max).log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= min", actual, min)(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: ${min.value.toString}"),
        Result
          .diffNamed(s"actual should be <= max", actual, max)(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: ${max.value.toString}")
      )
    )

  def testGenMillis: Property =
    for {
      actual <- TimeGens.genMillis.log("actual")
    } yield Result.all(
      List(
        Result
          .diffNamed(s"actual should be >= 0", actual, Millis.unsafeFrom(0))(_.value >= _.value)
          .log(s"actual: ${actual.value.toString}, min: 0"),
        Result
          .diffNamed(s"actual should be <= 999", actual, Millis.unsafeFrom(999))(_.value <= _.value)
          .log(s"actual: ${actual.value.toString}, max: 999")
      )
    )

}
