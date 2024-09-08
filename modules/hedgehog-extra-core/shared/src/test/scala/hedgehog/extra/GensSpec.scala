package hedgehog.extra

import hedgehog._
import hedgehog.runner._

import scala.util.control.NonFatal

/** @author Kevin Lee
  * @since 2021-04-07
  */
object GensSpec extends Properties {

  override def tests: List[Test] = List(
    property("testGenNonWhitespaceChar", testGenNonWhitespaceChar).withTests(10000),
    property("testGenNonWhitespaceStringUnsafe", testGenNonWhitespaceStringUnsafe).withTests(10000),
    property(
      "testGenNonWhitespaceStringUnsafeWithNonPositiveMaxLength",
      testGenNonWhitespaceStringUnsafeWithNonPositiveMaxLength
    ).withTests(1000),
    property("test genUnsafeNonWhitespaceStringMinMax", testGenUnsafeNonWhitespaceStringMinMax).withTests(10000),
    property(
      "test genUnsafeNonWhitespaceStringMinMax with non-positive minLength",
      testGenUnsafeNonWhitespaceStringMinMaxWithNonPositiveMinLength
    ).withTests(1000),
    property(
      "test genUnsafeNonWhitespaceStringMinMax with minLength > maxLength",
      testGenUnsafeNonWhitespaceStringMinMaxWithMinLengthGreaterThanMaxLength
    ).withTests(1000)
  )

  def testGenNonWhitespaceChar: Property = for {
    nonWhitespaceChar <- Gens.genNonWhitespaceChar.log("nonWhitespaceChar")
  } yield {
    (nonWhitespaceChar.isWhitespace ==== false)
      .log(s"nonSpaceChar should be non-whitespace char but it was. '${nonWhitespaceChar.toString}' (${"\\u%04x"
          .format(nonWhitespaceChar.toInt)} / ${nonWhitespaceChar.toInt.toString})")
  }

  def testGenNonWhitespaceStringUnsafe: Property = for {
    maxLength           <- Gen.int(Range.linear(1, 300)).log("maxLength")
    nonWhitespaceString <- Gens.genUnsafeNonWhitespaceString(maxLength).log("nonWhitespaceString")
  } yield {
    (nonWhitespaceString.exists(_.isWhitespace) ==== false)
      .log(
        s"nonWhitespaceString should not contain any whitespace char but it has. " +
          s"'$nonWhitespaceString' " +
          s"(${nonWhitespaceString.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
          s"${nonWhitespaceString.map(_.toInt).mkString("[", ",", "]")})"
      )
  }

  def testGenNonWhitespaceStringUnsafeWithNonPositiveMaxLength: Property = for {
    maxLength <- Gen.int(Range.linear(Int.MinValue, 0)).log("maxLength")
  } yield {
    try {
      val _ = Gens.genUnsafeNonWhitespaceString(maxLength).log("nonWhitespaceString")
      Result
        .failure
        .log(
          "IllegalArgumentException was expected but nothing was thrown."
        )
    } catch {
      case ex: IllegalArgumentException =>
        val expectedMessage =
          s"maxLength for genUnsafeNonWhitespaceString should be a positive Int (> 0). [maxLength: ${maxLength.toString}]"
        ex.getMessage ==== expectedMessage

      case NonFatal(ex) =>
        Result
          .failure
          .log(
            s"""IllegalArgumentException was expected but ${ex.getClass.getSimpleName} was thrown instead.
               |  ex: ${ex.getMessage}
               |""".stripMargin
          )
    }
  }

  def testGenUnsafeNonWhitespaceStringMinMax: Property = for {
    minLength <- Gen.int(Range.linear(1, 10)).log("minLength")
    maxLength <- Gen.int(Range.linear(minLength, 300)).log("maxLength")

    nonWhitespaceString <- Gens.genUnsafeNonWhitespaceStringMinMax(minLength, maxLength).log("nonWhitespaceString")
  } yield {
    (nonWhitespaceString.exists(_.isWhitespace) ==== false)
      .log(
        s"nonWhitespaceString should not contain any whitespace char but it has. " +
          s"'$nonWhitespaceString' " +
          s"(${nonWhitespaceString.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
          s"${nonWhitespaceString.map(_.toInt).mkString("[", ",", "]")})"
      )
  }

  def testGenUnsafeNonWhitespaceStringMinMaxWithNonPositiveMinLength: Property = for {
    minLength <- Gen.int(Range.linear(Int.MinValue, 0)).log("minLength")
    maxLength <- Gen.int(Range.linear(1, 300)).log("maxLength")
  } yield {
    try {
      val _ = Gens.genUnsafeNonWhitespaceStringMinMax(minLength, maxLength).log("nonWhitespaceString")
      Result
        .failure
        .log(
          "IllegalArgumentException was expected but nothing was thrown."
        )
    } catch {
      case ex: IllegalArgumentException =>
        val expectedMessage =
          s"minLength for genUnsafeNonWhitespaceStringMinMax should be a positive Int (> 0). [minLength: ${minLength.toString}]"
        ex.getMessage ==== expectedMessage

      case NonFatal(ex) =>
        Result
          .failure
          .log(
            s"""IllegalArgumentException was expected but ${ex.getClass.getSimpleName} was thrown instead.
               |  ex: ${ex.getMessage}
               |""".stripMargin
          )
    }
  }

  def testGenUnsafeNonWhitespaceStringMinMaxWithMinLengthGreaterThanMaxLength: Property = for {
    minLength <- Gen.int(Range.linear(301, 1000)).log("minLength")
    maxLength <- Gen.int(Range.linear(1, 300)).log("maxLength")
  } yield {
    try {
      val _ = Gens.genUnsafeNonWhitespaceStringMinMax(minLength, maxLength).log("nonWhitespaceString")
      Result
        .failure
        .log(
          "IllegalArgumentException was expected but nothing was thrown."
        )
    } catch {
      case ex: IllegalArgumentException =>
        val expectedMessage =
          "maxLength for genUnsafeNonWhitespaceStringMinMax is less than minLength. " +
            "maxLength for genUnsafeNonWhitespaceStringMinMax should be greater than or equal to minLength (minLength <= maxLength). " +
            s"[minLength: ${minLength.toString}, maxLength: ${maxLength.toString}]"
        ex.getMessage ==== expectedMessage

      case NonFatal(ex) =>
        Result
          .failure
          .log(
            s"""IllegalArgumentException was expected but ${ex.getClass.getSimpleName} was thrown instead.
               |  ex: ${ex.getMessage}
               |""".stripMargin
          )
    }
  }

}
