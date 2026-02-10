---
sidebar_position: 3
id: refined4s-network-gens
title: "NetworkGens"
---

# `refined4s` `NetworkGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined4s.gens.NetworkGens
import refined4s.types.all.*
```

## Port generators

```scala mdoc
val genPortNumber: Gen[PortNumber] =
  NetworkGens.genPortNumber

val genSystemPortNumber: Gen[SystemPortNumber] =
  NetworkGens.genSystemPortNumber

val genUserPortNumber: Gen[UserPortNumber] =
  NetworkGens.genUserPortNumber

val genDynamicPortNumber: Gen[DynamicPortNumber] =
  NetworkGens.genDynamicPortNumber

val genNonSystemPortNumber: Gen[NonSystemPortNumber] =
  NetworkGens.genNonSystemPortNumber
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined4s.gens.NetworkGens

object NetworkGensExampleSpec extends Properties {
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
      p <- NetworkGens.genPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 0", p.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }

  def testExampleForGenSystemPortNumber: Property =
    for {
      p <- NetworkGens.genSystemPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 0", p.value, 0)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 1023", p.value, 1023)(_ <= _),
      ))
    }

  def testExampleForGenUserPortNumber: Property =
    for {
      p <- NetworkGens.genUserPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 1024", p.value, 1024)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 49151", p.value, 49151)(_ <= _),
      ))
    }

  def testExampleForGenDynamicPortNumber: Property =
    for {
      p <- NetworkGens.genDynamicPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 49152", p.value, 49152)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }

  def testExampleForGenNonSystemPortNumber: Property =
    for {
      p <- NetworkGens.genNonSystemPortNumber.log("p")
    } yield {
      println(s"p: $p") // only for documentation to show the value
      Result.all(List(
        Result.diffNamed(s"The value [$p] must be >= 1024", p.value, 1024)(_ >= _),
        Result.diffNamed(s"The value [$p] must be <= 65535", p.value, 65535)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NetworkGensExampleSpec.main(Array.empty)
```
