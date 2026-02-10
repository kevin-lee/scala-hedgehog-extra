---
sidebar_position: 3
id: refined-net-gens
title: "NetGens"
---

# `refined` `NetGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined.NetGens
import eu.timepit.refined.types.net.*
```

## Port generators

```scala mdoc
val genPortNumber: Gen[PortNumber] =
  NetGens.genPortNumber

val genSystemPortNumber: Gen[SystemPortNumber] =
  NetGens.genSystemPortNumber

val genUserPortNumber: Gen[UserPortNumber] =
  NetGens.genUserPortNumber

val genDynamicPortNumber: Gen[DynamicPortNumber] =
  NetGens.genDynamicPortNumber

val genNonSystemPortNumber: Gen[NonSystemPortNumber] =
  NetGens.genNonSystemPortNumber
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.NetGens

object NetGensExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genPortNumber",
      testExampleForGenPortNumber
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genSystemPortNumber",
      testExampleForGenSystemPortNumber
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genUserPortNumber",
      testExampleForGenUserPortNumber
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genDynamicPortNumber",
      testExampleForGenDynamicPortNumber
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonSystemPortNumber",
      testExampleForGenNonSystemPortNumber
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenPortNumber: Property =
    for {
      p <- NetGens.genPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 0", p.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }

  def testExampleForGenSystemPortNumber: Property =
    for {
      p <- NetGens.genSystemPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 0", p.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 1023", p.value, 1023)(_ <= _),
      ))
    }

  def testExampleForGenUserPortNumber: Property =
    for {
      p <- NetGens.genUserPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 1024", p.value, 1024)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 49151", p.value, 49151)(_ <= _),
      ))
    }

  def testExampleForGenDynamicPortNumber: Property =
    for {
      p <- NetGens.genDynamicPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 49152", p.value, 49152)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }

  def testExampleForGenNonSystemPortNumber: Property =
    for {
      p <- NetGens.genNonSystemPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 1024", p.value, 1024)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NetGensExampleSpec.main(Array.empty)
```
