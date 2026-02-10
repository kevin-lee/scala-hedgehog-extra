---
sidebar_position: 3
id: core-time-gens
title: "TimeGens"
---

# `TimeGens`

`TimeGens` provides generators for `Instant` and `LocalDate` ranges.

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.TimeGens
import java.time.{Instant, LocalDate}
import scala.concurrent.duration.*
```

## `Instant` generators

```scala mdoc
val fromInstant = Instant.parse("2026-01-01T00:00:00Z")
val toInstant = Instant.parse("2026-12-31T23:59:59Z")

val instantGen: Gen[Instant] =
  TimeGens.genInstant(fromInstant, toInstant)

val baseInstant = Instant.parse("2026-06-01T00:00:00Z")
val aroundBaseInstantGen: Gen[Instant] =
  TimeGens.genInstantFrom(baseInstant, 7.days, 14.days)
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object InstantGeneratorsExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for instantGen",
      testExampleForInstantGen
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for aroundBaseInstantGen",
      testExampleForAroundBaseInstantGen
    ).withTests(5) // only for documentation to show fewer logs
  )

  def testExampleForInstantGen: Property =
    for {
      actual <- instantGen.log("actual")
    } yield {
      println(s"instant: $actual") // only for documentation to show the value
      Result
        .assert(!actual.isBefore(fromInstant) && !actual.isAfter(toInstant))
        .log("Generated Instant should be in [fromInstant, toInstant]")
    }

  def testExampleForAroundBaseInstantGen: Property = {
    val expectedFrom = baseInstant.minusMillis(7.days.toMillis)
    val expectedTo   = baseInstant.plusMillis(14.days.toMillis)

    for {
      actual <- aroundBaseInstantGen.log("actual")
    } yield {
      println(s"aroundBaseInstant: $actual") // only for documentation to show the value
      Result
        .assert(!actual.isBefore(expectedFrom) && !actual.isAfter(expectedTo))
        .log("Generated Instant should be in [baseInstant - 7.days, baseInstant + 14.days]")
    }
  }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
InstantGeneratorsExampleSpec.main(Array.empty)
```

## `LocalDate` generators

```scala mdoc
val fromDate = LocalDate.parse("2026-01-01")
val toDate = LocalDate.parse("2026-12-31")

val localDateGen: Gen[LocalDate] =
  TimeGens.genLocalDate(fromDate, toDate)

val baseDate = LocalDate.parse("2026-06-01")
val aroundBaseDateGen: Gen[LocalDate] =
  TimeGens.genLocalDateFrom(baseDate, 30.days, 30.days)
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object LocalDateGeneratorsExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for localDateGen",
      testExampleForLocalDateGen
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for aroundBaseDateGen",
      testExampleForAroundBaseDateGen
    ).withTests(5) // only for documentation to show fewer logs
  )

  def testExampleForLocalDateGen: Property =
    for {
      actual <- localDateGen.log("actual")
    } yield {
      println(s"localDate: $actual") // only for documentation to show the value
      Result
        .assert(
          (actual.isEqual(fromDate) || actual.isAfter(fromDate)) &&
            (actual.isEqual(toDate) || actual.isBefore(toDate))
        )
        .log("Generated LocalDate should be in [fromDate, toDate]")
    }

  def testExampleForAroundBaseDateGen: Property = {
    val expectedFrom = baseDate.minusDays(30.days.toDays)
    val expectedTo   = baseDate.plusDays(30.days.toDays)

    for {
      actual <- aroundBaseDateGen.log("actual")
    } yield {
      println(s"aroundBaseDate: $actual") // only for documentation to show the value
      Result
        .assert(
          (actual.isEqual(expectedFrom) || actual.isAfter(expectedFrom)) &&
            (actual.isEqual(expectedTo) || actual.isBefore(expectedTo))
        )
        .log("Generated LocalDate should be in [baseDate - 30.days, baseDate + 30.days]")
    }
  }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
LocalDateGeneratorsExampleSpec.main(Array.empty)
```
