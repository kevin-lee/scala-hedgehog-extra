---
sidebar_position: 1
id: core-gens
title: "Gens"
---

# `Gens`

`Gens` provides foundational character and string generators.

## Import

```scala mdoc
import hedgehog.*
import hedgehog.extra.Gens
```

## Non-whitespace character generator

```scala mdoc
val nonWhitespaceCharGen: Gen[Char] =
  Gens.genNonWhitespaceChar
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object NonWhitespaceExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for genNonWhitespaceChar",
      testExampleForGenNonWhitespaceChar
    ).withTests(5) // only for documentation to show fewer logs
  )
  
  def testExampleForGenNonWhitespaceChar: Property =
    for {
      c <- nonWhitespaceCharGen.log("c")
    } yield {
      println(s"c: $c") // only for documentation to show the value
      c.isWhitespace ==== false
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NonWhitespaceExampleSpec.main(Array.empty)
```

## Character generator from custom ranges

```scala mdoc
val alphaNumericLikeCharGen: Gen[Char] =
  Gens.genCharByRange(
    List(
      48 -> 57,
      65 -> 90,
      97 -> 122
    )
  )
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object AlphaNumericLikeCharGenSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for alphaNumericLikeCharGen",
      testExampleForAlphaNumericLikeCharGen
    ).withTests(5) // only for documentation to show fewer logs
  )
  
  def testExampleForAlphaNumericLikeCharGen: Property =
    for {
      c <- alphaNumericLikeCharGen.log("c")
    } yield {
      println(s"c: $c") // only for documentation to show the value
      Result.assert(c.isLetterOrDigit).log("It should be alphanumeric char")
    }
}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
AlphaNumericLikeCharGenSpec.main(Array.empty)
```


## Non-whitespace string generators

```scala mdoc
val tokenGen: Gen[String] =
  Gens.genUnsafeNonWhitespaceString(16)

val boundedTokenGen: Gen[String] =
  Gens.genUnsafeNonWhitespaceStringMinMax(4, 12)
```

```scala mdoc
import hedgehog.*
import hedgehog.runner.*

object NonWhitespaceStringExampleSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "Example for tokenGen",
      testExampleForTokenGen
    ).withTests(5), // only for documentation to show fewer logs
    property(
      "Example for boundedTokenGen",
      testExampleForBoundedTokenGen
    ).withTests(5) // only for documentation to show fewer logs
  )
  
  def testExampleForTokenGen: Property =
    for {
      s <- tokenGen.log("s")
    } yield {
      println(s"s: $s") // only for documentation to show the value
      Result.all(
        s.map(_.isWhitespace ==== false).toList
      )
    }
  
  def testExampleForBoundedTokenGen: Property =
    for {
      s2 <- boundedTokenGen.log("s2")
    } yield {
      println(s"s2: $s2") // only for documentation to show the value
      Result.all(
        s2.map(_.isWhitespace ==== false).toList
      )
    }

}

// This is only for doc. Your test code should be run by sbt (or maybe another build tool)
NonWhitespaceStringExampleSpec.main(Array.empty)
```

## Constraints

These are intentionally `unsafe` APIs and throw `IllegalArgumentException` for invalid bounds.

```scala mdoc:crash
Gens.genUnsafeNonWhitespaceString(0)
```
```scala mdoc:crash
Gens.genUnsafeNonWhitespaceStringMinMax(0, 10)
```
```scala mdoc:crash
Gens.genUnsafeNonWhitespaceStringMinMax(10, 5)
```
