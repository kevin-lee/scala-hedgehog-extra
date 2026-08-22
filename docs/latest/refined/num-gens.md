---
sidebar_position: 1
id: refined-num-gens
title: "NumGens"
---

# `refined` `NumGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined.NumGens
import eu.timepit.refined.types.numeric.*
```

## Numeric refined generators

```scala mdoc
val genNegInt: Gen[NegInt] =
  NumGens.genNegInt(NegInt.unsafeFrom(-100), NegInt.unsafeFrom(-1))

val genNonPosInt: Gen[NonPosInt] =
  NumGens.genNonPosInt(NonPosInt.unsafeFrom(-100), NonPosInt.unsafeFrom(0))

val genPosInt: Gen[PosInt] =
  NumGens.genPosInt(PosInt.unsafeFrom(1), PosInt.unsafeFrom(100))

val genNonNegInt: Gen[NonNegInt] =
  NumGens.genNonNegInt(NonNegInt.unsafeFrom(0), NonNegInt.unsafeFrom(100))

val genNegLong: Gen[NegLong] =
  NumGens.genNegLong(NegLong.unsafeFrom(-1000L), NegLong.unsafeFrom(-1L))

val genNonPosLong: Gen[NonPosLong] =
  NumGens.genNonPosLong(NonPosLong.unsafeFrom(-1000L), NonPosLong.unsafeFrom(0L))

val genPosLong: Gen[PosLong] =
  NumGens.genPosLong(PosLong.unsafeFrom(1L), PosLong.unsafeFrom(1000L))

val genNonNegLong: Gen[NonNegLong] =
  NumGens.genNonNegLong(NonNegLong.unsafeFrom(0L), NonNegLong.unsafeFrom(1000L))

val genNegDouble: Gen[NegDouble] =
  NumGens.genNegDouble(NegDouble.unsafeFrom(-100.0d), NegDouble.unsafeFrom(-0.1d))

val genNonPosDouble: Gen[NonPosDouble] =
  NumGens.genNonPosDouble(NonPosDouble.unsafeFrom(-10.0), NonPosDouble.unsafeFrom(0.0))

val genPosDouble: Gen[PosDouble] =
  NumGens.genPosDouble(PosDouble.unsafeFrom(0.1d), PosDouble.unsafeFrom(100.0d))

val genNonNegDouble: Gen[NonNegDouble] =
  NumGens.genNonNegDouble(NonNegDouble.unsafeFrom(0.0d), NonNegDouble.unsafeFrom(100.0d))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.NumGens

import eu.timepit.refined.types.numeric.*

object NumGensExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNegInt",
      testExampleForGenNegInt
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genPosInt",
      testExampleForGenPosInt
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonNegLong",
      testExampleForGenNonNegLong
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonPosDouble",
      testExampleForGenNonPosDouble
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNegInt: Property =
    for {
      n <- NumGens.genNegInt(NegInt.unsafeFrom(-100), NegInt.unsafeFrom(-1)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= -100", n.value, -100)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= -1", n.value, -1)(_ <= _),
      ))
    }

  def testExampleForGenPosInt: Property =
    for {
      n <- NumGens.genPosInt(PosInt.unsafeFrom(1), PosInt.unsafeFrom(100)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1", n.value, 1)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 100", n.value, 100)(_ <= _),
      ))
    }

  def testExampleForGenNonNegLong: Property =
    for {
      n <- NumGens.genNonNegLong(NonNegLong.unsafeFrom(0L), NonNegLong.unsafeFrom(1000L)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 0L", n.value, 0L)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 1000L", n.value, 1000L)(_ <= _),
      ))
    }

  def testExampleForGenNonPosDouble: Property =
    for {
      n <- NumGens.genNonPosDouble(NonPosDouble.unsafeFrom(-10.0), NonPosDouble.unsafeFrom(0.0)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= -10.0d", n.value, -10.0d)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 0.0d", n.value, 0.0d)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NumGensExampleSpec.main(Array.empty)
```

## Helper variants

```scala mdoc
val genNegIntMinTo: Gen[NegInt] =
  NumGens.genNegIntMinTo(NegInt.unsafeFrom(-500))

val genNonPosIntMinTo: Gen[NonPosInt] =
  NumGens.genNonPosIntMinTo(NonPosInt.unsafeFrom(-500))

val genPosIntMaxTo: Gen[PosInt] =
  NumGens.genPosIntMaxTo(PosInt.unsafeFrom(1000))

val genNonNegIntMaxTo: Gen[NonNegInt] =
  NumGens.genNonNegIntMaxTo(NonNegInt.unsafeFrom(1000))

val genNegLongMinTo: Gen[NegLong] =
  NumGens.genNegLongMinTo(NegLong.unsafeFrom(-10000L))

val genNonPosLongMinTo: Gen[NonPosLong] =
  NumGens.genNonPosLongMinTo(NonPosLong.unsafeFrom(-10000L))

val genPosLongMaxTo: Gen[PosLong] =
  NumGens.genPosLongMaxTo(PosLong.unsafeFrom(10000L))

val genNonNegLongMaxTo: Gen[NonNegLong] =
  NumGens.genNonNegLongMaxTo(NonNegLong.unsafeFrom(10000L))

val genNegDoubleMinTo: Gen[NegDouble] =
  NumGens.genNegDoubleMinTo(NegDouble.unsafeFrom(-500.0d))

val genNonPosDoubleMinTo: Gen[NonPosDouble] =
  NumGens.genNonPosDoubleMinTo(NonPosDouble.unsafeFrom(-500.0d))

val genPosDoubleMaxTo: Gen[PosDouble] =
  NumGens.genPosDoubleMaxTo(PosDouble.unsafeFrom(1000.0d))

val genNonNegDoubleMaxTo: Gen[NonNegDouble] =
  NumGens.genNonNegDoubleMaxTo(NonNegDouble.unsafeFrom(1000.0d))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.NumGens

import eu.timepit.refined.types.numeric.*

object NumGensVariantsExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNegIntMinTo",
      testExampleForGenNegIntMinTo
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genPosLongMaxTo",
      testExampleForGenPosLongMaxTo
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNegIntMinTo: Property =
    for {
      n <- NumGens.genNegIntMinTo(NegInt.unsafeFrom(-500)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= -500", n.value, -500)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= -1", n.value, -1)(_ <= _),
      ))
    }

  def testExampleForGenPosLongMaxTo: Property =
    for {
      n <- NumGens.genPosLongMaxTo(PosLong.unsafeFrom(10000L)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1L", n.value, 1L)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 10000L", n.value, 10000L)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NumGensVariantsExampleSpec.main(Array.empty)
```
