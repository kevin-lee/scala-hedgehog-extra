---
sidebar_position: 4
id: refined-time-gens
title: "TimeGens"
---

# `refined` `TimeGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined.TimeGens
import eu.timepit.refined.types.time.*
```

## Time-part generators

```scala mdoc
val genMonthMinMax: Gen[Month] =
  TimeGens.genMonthMinMax(Month.unsafeFrom(1), Month.unsafeFrom(12))

val genMonth: Gen[Month] =
  TimeGens.genMonth

val genDayMinMax: Gen[Day] =
  TimeGens.genDayMinMax(Day.unsafeFrom(1), Day.unsafeFrom(31))

val genDay: Gen[Day] =
  TimeGens.genDay

val genHourMinMax: Gen[Hour] =
  TimeGens.genHourMinMax(Hour.unsafeFrom(0), Hour.unsafeFrom(23))

val genHour: Gen[Hour] =
  TimeGens.genHour

val genMinuteMinMax: Gen[Minute] =
  TimeGens.genMinuteMinMax(Minute.unsafeFrom(0), Minute.unsafeFrom(59))

val genMinute: Gen[Minute] =
  TimeGens.genMinute

val genSecondMinMax: Gen[Second] =
  TimeGens.genSecondMinMax(Second.unsafeFrom(0), Second.unsafeFrom(59))

val genSecond: Gen[Second] =
  TimeGens.genSecond

val genMillisMinMax: Gen[Millis] =
  TimeGens.genMillisMinMax(Millis.unsafeFrom(0), Millis.unsafeFrom(999))

val genMillis: Gen[Millis] =
  TimeGens.genMillis
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.TimeGens

import eu.timepit.refined.types.time.*

object TimeGensExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genMonth",
      testExampleForGenMonth
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genBusinessHour",
      testExampleForGenBusinessHour
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genSecond",
      testExampleForGenSecond
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genMillis",
      testExampleForGenMillis
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenMonth: Property =
    for {
      n <- TimeGens.genMonth.log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1", n.value, 1)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 12", n.value, 12)(_ <= _),
      ))
    }

  def testExampleForGenBusinessHour: Property =
    for {
      n <- TimeGens.genHourMinMax(Hour.unsafeFrom(9), Hour.unsafeFrom(17)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 9", n.value, 9)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 17", n.value, 17)(_ <= _),
      ))
    }

  def testExampleForGenSecond: Property =
    for {
      n <- TimeGens.genSecond.log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 0", n.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 59", n.value, 59)(_ <= _),
      ))
    }

  def testExampleForGenMillis: Property =
    for {
      n <- TimeGens.genMillis.log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 0", n.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 999", n.value, 999)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
TimeGensExampleSpec.main(Array.empty)
```

## Bounded generators

```scala mdoc
val genFirstQuarterMonth: Gen[Month] =
  TimeGens.genMonthMinMax(Month.unsafeFrom(1), Month.unsafeFrom(3))

val genFirstTenDays: Gen[Day] =
  TimeGens.genDayMinMax(Day.unsafeFrom(1), Day.unsafeFrom(10))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.TimeGens

import eu.timepit.refined.types.time.*

object TimeGensBoundedExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genFirstQuarterMonth",
      testExampleForGenFirstQuarterMonth
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genFirstTenDays",
      testExampleForGenFirstTenDays
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenFirstQuarterMonth: Property =
    for {
      n <- TimeGens.genMonthMinMax(Month.unsafeFrom(1), Month.unsafeFrom(3)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1", n.value, 1)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 3", n.value, 3)(_ <= _),
      ))
    }

  def testExampleForGenFirstTenDays: Property =
    for {
      n <- TimeGens.genDayMinMax(Day.unsafeFrom(1), Day.unsafeFrom(10)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1", n.value, 1)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 10", n.value, 10)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
TimeGensBoundedExampleSpec.main(Array.empty)
```
