---
sidebar_position: 1
id: refined4s-num-gens
title: "NumGens"
---

# `refined4s` `NumGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined4s.gens.NumGens
import refined4s.types.all.*
```

## Numeric refined generators

```scala mdoc
val genNegInt: Gen[NegInt] =
  NumGens.genNegInt(NegInt(-100), NegInt(-1))

val genPosInt: Gen[PosInt] =
  NumGens.genPosInt(PosInt(1), PosInt(100))

val genNonNegLong: Gen[NonNegLong] =
  NumGens.genNonNegLong(NonNegLong(0L), NonNegLong(1000L))

val genPosDouble: Gen[PosDouble] =
  NumGens.genPosDouble(PosDouble(0.1d), PosDouble(100d))

// and there are more... 
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined4s.gens.NumGens

import refined4s.types.all.*

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
      "Example for genPosDouble",
      testExampleForGenPosDouble
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNegInt: Property =
    for {
      n <- NumGens.genNegInt(NegInt(-100), NegInt(-1)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= -100", n.value, -100)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= -1", n.value, -1)(_ <= _),
      ))
    }
  
  def testExampleForGenPosInt: Property =
    for {
      n <- NumGens.genPosInt(PosInt(1), PosInt(100)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 1", n.value, 1)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 100", n.value, 100)(_ <= _),
      ))
    }

  def testExampleForGenNonNegLong: Property =
    for {
      n <- NumGens.genNonNegLong(NonNegLong(0L), NonNegLong(1000L)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 0L", n.value, 0L)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 1000L", n.value, 1000L)(_ <= _),
      ))
    }
  
  def testExampleForGenPosDouble: Property =
    for {
      n <- NumGens.genPosDouble(PosDouble(0.1d), PosDouble(100d)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$n] must be >= 0.1d", n.value, 0.1d)(_ >= _),
        Result.diffNamed(s"The value [$n] must be <= 100d", n.value, 100d)(_ <= _),
      ))
    }

}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NumGensExampleSpec.main(Array.empty)
```


## Helper variants

```scala mdoc
val anyNegIntFrom: Gen[NegInt] =
  NumGens.genNegIntMinTo(NegInt(-500))

val anyNonNegIntUpTo: Gen[NonNegInt] =
  NumGens.genNonNegIntMaxTo(NonNegInt(1000))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined4s.gens.NumGens

import refined4s.types.all.*

object NumGensVariantsExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNegIntMinTo",
      testExampleForGenNegIntMinTo
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonNegIntMaxTo",
      testExampleForGenNonNegIntMaxTo
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNegIntMinTo: Property =
    for {
      n <- NumGens.genNegIntMinTo(NegInt(-500)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.diffNamed(s"The value [$n] must be >= -500", n.value, -500)(_ >= _)
    }
  
  def testExampleForGenNonNegIntMaxTo: Property =
    for {
      n <- NumGens.genNonNegIntMaxTo(NonNegInt(1000)).log("n")
    } yield {
      println(s"n: $n") // only for documentation to show the value
      Result.diffNamed(s"The value [$n] must be <= 1000", n.value, 1000)(_ <= _)
    }

}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NumGensVariantsExampleSpec.main(Array.empty)
```
