---
sidebar_position: 2
id: refined-string-gens
title: "StringGens"
---

# `refined` `StringGens`

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.refined.StringGens
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString
```

## Non-whitespace string generators

```scala mdoc
val genNonWhitespaceString: Gen[NonEmptyString] =
  StringGens.genNonWhitespaceString(PosInt.unsafeFrom(20))

val genNonWhitespaceStringMinMax: Gen[NonEmptyString] =
  StringGens.genNonWhitespaceStringMinMax(PosInt.unsafeFrom(3), PosInt.unsafeFrom(12))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.StringGens

import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString

object StringGensExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNonWhitespaceString",
      testExampleForGenNonWhitespaceString
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonWhitespaceStringMinMax",
      testExampleForGenNonWhitespaceStringMinMax
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNonWhitespaceString: Property =
    for {
      s <- StringGens.genNonWhitespaceString(PosInt.unsafeFrom(20)).log("s")
    } yield {
      println(s"s: $s") // only for documentation to show the value
      Result.all(List(
        Result.assert(s.value.nonEmpty).log(s"The value [$s] must be non-empty"),
        Result.assert(!s.value.exists(_.isWhitespace)).log(s"The value [$s] must not contain whitespace"),
      ))
    }

  def testExampleForGenNonWhitespaceStringMinMax: Property =
    for {
      s <- StringGens.genNonWhitespaceStringMinMax(PosInt.unsafeFrom(3), PosInt.unsafeFrom(12)).log("s")
    } yield {
      println(s"s: $s") // only for documentation to show the value
      Result.all(List(
        Result.assert(s.value.nonEmpty).log(s"The value [$s] must be non-empty"),
        Result.assert(!s.value.exists(_.isWhitespace)).log(s"The value [$s] must not contain whitespace"),
        Result.diffNamed(s"The length of [$s] must be >= 3", s.value.length, 3)(_ >= _),
        Result.diffNamed(s"The length of [$s] must be <= 12", s.value.length, 12)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
StringGensExampleSpec.main(Array.empty)
```

## Custom character generator variants

```scala mdoc
val genNonEmptyStringAlphaNum: Gen[NonEmptyString] =
  StringGens.genNonEmptyString(Gen.alphaNum, PosInt.unsafeFrom(20))

val genNonEmptyStringUnicode: Gen[NonEmptyString] =
  StringGens.genNonEmptyStringMinMax(Gen.unicodeAll, PosInt.unsafeFrom(2), PosInt.unsafeFrom(10))
```

```scala mdoc:reset
import hedgehog.*
import hedgehog.runner.*

import hedgehog.extra.refined.StringGens

import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString

object StringGensCustomCharExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNonEmptyStringAlphaNum",
      testExampleForGenNonEmptyStringAlphaNum
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for genNonEmptyStringUnicode",
      testExampleForGenNonEmptyStringUnicode
    ).withTests(5), // only for documentation to show fewer logs
  )

  def testExampleForGenNonEmptyStringAlphaNum: Property =
    for {
      s <- StringGens.genNonEmptyString(Gen.alphaNum, PosInt.unsafeFrom(20)).log("s")
    } yield {
      println(s"s: $s") // only for documentation to show the value
      Result.all(List(
        Result.assert(s.value.nonEmpty).log(s"The value [$s] must be non-empty"),
        Result
          .assert(s.value.forall(c => c.isDigit || ('a' to 'z').contains(c) || ('A' to 'Z').contains(c)))
          .log(s"The value [$s] must contain only alphabet or digit"),
      ))
    }

  def testExampleForGenNonEmptyStringUnicode: Property =
    for {
      s <- StringGens.genNonEmptyStringMinMax(Gen.unicodeAll, PosInt.unsafeFrom(2), PosInt.unsafeFrom(10)).log("s")
    } yield {
      println(s"s: $s") // only for documentation to show the value
      Result.all(List(
        Result.assert(s.value.nonEmpty).log(s"The value [$s] must be non-empty"),
        Result.diffNamed(s"The length of [$s] must be >= 2", s.value.length, 2)(_ >= _),
        Result.diffNamed(s"The length of [$s] must be <= 10", s.value.length, 10)(_ <= _),
      ))
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
StringGensCustomCharExampleSpec.main(Array.empty)
```
