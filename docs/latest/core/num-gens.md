---
sidebar_position: 2
id: core-num-gens
title: "NumGens"
---

# `NumGens`

`NumGens` provides generators that always produce ordered min/max pairs.

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.NumGens
```

## Ordered pair generators

```scala mdoc
val intPairGen: Gen[(Int, Int)] =
  NumGens.genIntMinMaxPair(-100, 100)

val longPairGen: Gen[(Long, Long)] =
  NumGens.genLongMinMaxPair(-1000L, 1000L)
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object OrderedPairGeneratorsExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for intPairGen",
      testExampleForIntPairGen
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for longPairGen",
      testExampleForLongPairGen
    ).withTests(5) // only for documentation to show fewer logs
  )

  def testExampleForIntPairGen: Property =
    for {
      pair <- intPairGen.log("pair")
      (min, max) = pair
    } yield {
      println(s"intPair: ($min, $max)") // only for documentation to show the value
      Result.assert(min <= max).log("Int min/max pair should satisfy min <= max")
    }

  def testExampleForLongPairGen: Property =
    for {
      pair <- longPairGen.log("pair")
      (min, max) = pair
    } yield {
      println(s"longPair: ($min, $max)") // only for documentation to show the value
      Result.assert(min <= max).log("Long min/max pair should satisfy min <= max")
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
OrderedPairGeneratorsExampleSpec.main(Array.empty)
```

## Functional composition with `Range`

```scala mdoc
val boundedIntGen: Gen[Int] =
  NumGens
    .genIntMinMaxPair(1, 50)
    .flatMap { case (min, max) =>
      Gen.int(Range.linear(min, max))
    }
```

```scala mdoc
val boundedIntWithRangeGen: Gen[(Int, Int, Int)] =
  NumGens
    .genIntMinMaxPair(1, 50)
    .flatMap { case (min, max) =>
      Gen.int(Range.linear(min, max)).map { value =>
        (min, max, value)
      }
    }
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object BoundedIntGeneratorExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for boundedIntGen",
      testExampleForBoundedIntGen
    ).withTests(5) // only for documentation to show fewer logs
  )

  def testExampleForBoundedIntGen: Property =
    for {
      tuple <- boundedIntWithRangeGen.log("(min, max, value)")
      (min, max, value) = tuple
    } yield {
      println(s"(min, max, value): ($min, $max, $value)") // only for documentation to show the value
      Result
        .assert(value >= min && value <= max)
        .log("Generated value should stay within its generated range")
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
BoundedIntGeneratorExampleSpec.main(Array.empty)
```
