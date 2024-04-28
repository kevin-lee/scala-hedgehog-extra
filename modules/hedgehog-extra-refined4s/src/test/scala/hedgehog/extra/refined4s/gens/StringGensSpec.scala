package hedgehog.extra.refined4s.gens

import cats.syntax.all.*
import hedgehog.*
import hedgehog.runner.*
import refined4s.modules.cats.derivation.types.all.given
import refined4s.types.all.*

/** @author Kevin Lee
  * @since 2021-04-07
  */
object StringGensSpec extends Properties {

  override def tests: List[Test] = List(
    property("test StringGens.genNonWhitespaceString", testGenNonWhitespaceString).withTests(10000),
    property("test StringGens.genNonWhitespaceStringMinMax", testGenNonWhitespaceStringMinMax).withTests(10000),
    property("test StringGens.genNonEmptyString with Gen.alphaNum", testGenNonEmptyStringAlphaNum).withTests(10000),
    property("test StringGens.genNonEmptyString with Gen.unicode", testGenNonEmptyStringUnicode).withTests(10000),
    property(
      "test StringGens.genNonEmptyStringMinMax with Gen.alphaNum",
      testGenNonEmptyStringMinMaxAlphaNum
    ).withTests(10000),
    property(
      "test StringGens.genNonEmptyStringMinMax with Gen.unicode",
      testGenNonEmptyStringMinMaxUnicode
    ).withTests(10000),
    property("test StringGens.genNonBlankString", testGenNonBlankString).withTests(10000),
    property("test StringGens.genNonBlankStringMinMax", testGenNonBlankStringMinMax).withTests(10000),
    property("test StringGens.genUuid", testGenUuid).withTests(10),
  )

  def testGenNonWhitespaceString: Property = for {
    maxLength           <- NumGens.genPosIntMaxTo(PosInt(300)).log("maxLength")
    nonWhitespaceString <- StringGens.genNonWhitespaceString(maxLength).log("nonWhitespaceString")
  } yield {
    (nonWhitespaceString.value.exists(_.isWhitespace) ==== false)
      .log(
        "nonWhitespaceString should not contain any whitespace char but it has. " +
          show"'$nonWhitespaceString' " +
          show"(${nonWhitespaceString.value.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
          show"${nonWhitespaceString.value.map(_.toInt).mkString("[", ",", "]")})"
      )
  }

  def testGenNonWhitespaceStringMinMax: Property = for {
    minLength <- NumGens.genPosIntMaxTo(PosInt(10)).log("minLength")
    maxLength <- NumGens.genPosInt(minLength, PosInt(300)).log("maxLength")

    nonWhitespaceString <- StringGens.genNonWhitespaceStringMinMax(minLength, maxLength).log("nonWhitespaceString")
  } yield {
    Result.all(
      List(
        (nonWhitespaceString.value.exists(_.isWhitespace) ==== false)
          .log(
            "nonWhitespaceString should not contain any whitespace char but it has. " +
              show"'$nonWhitespaceString' " +
              show"(${nonWhitespaceString.value.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
              show"${nonWhitespaceString.value.map(_.toInt).mkString("[", ",", "]")})"
          ),
        Result.diffNamed(
          show"The length of nonWhitespaceString should be $minLength <= length <= $maxLength",
          minLength.value to maxLength.value,
          nonWhitespaceString.value.length
        )((range, actual) => range.exists(_ === actual))
      )
    )
  }

  def testGenNonEmptyStringAlphaNum: Property = for {
    maxLength      <- NumGens.genPosIntMaxTo(PosInt(300)).log("maxLength")
    nonEmptyString <- StringGens.genNonEmptyString(Gen.alphaNum, maxLength).log("nonEmptyString")
  } yield {
    Result.all(
      List(
        Result
          .assert(nonEmptyString.value.nonEmpty)
          .log(
            show"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty}"
          ),
        Result
          .assert(
            nonEmptyString.value.forall(c => c.isDigit || ('a' to 'z').contains(c) || ('A' to 'Z').contains(c))
          )
          .log("nonEmptyString should only contain alphabet or digit")
      )
    )
  }

  def testGenNonEmptyStringUnicode: Property = for {
    maxLength      <- NumGens.genPosIntMaxTo(PosInt(300)).log("maxLength")
    nonEmptyString <- StringGens.genNonEmptyString(Gen.unicodeAll, maxLength).log("nonEmptyString")
  } yield {
    Result
      .assert(nonEmptyString.value.nonEmpty)
      .log(
        show"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty}"
      )
  }

  def testGenNonEmptyStringMinMaxAlphaNum: Property = for {
    minLength      <- NumGens.genPosIntMaxTo(PosInt(10)).log("minLength")
    maxLength      <- NumGens.genPosInt(minLength, PosInt(300)).log("maxLength")
    nonEmptyString <- StringGens.genNonEmptyStringMinMax(Gen.alphaNum, minLength, maxLength).log("nonEmptyString")
  } yield {
    Result.all(
      List(
        Result
          .assert(nonEmptyString.value.nonEmpty)
          .log(
            show"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty}"
          ),
        Result
          .assert(
            nonEmptyString.value.forall(c => c.isDigit || ('a' to 'z').contains(c) || ('A' to 'Z').contains(c))
          )
          .log("nonEmptyString should only contain alphabet or digit"),
        Result.diffNamed(
          show"The length of monEmptyString should be $minLength <= length <= $maxLength",
          minLength.value to maxLength.value,
          nonEmptyString.value.length
        )((range, actual) => range.exists(_ === actual))
      )
    )
  }

  def testGenNonEmptyStringMinMaxUnicode: Property = for {
    minLength      <- NumGens.genPosIntMaxTo(PosInt(10)).log("minLength")
    maxLength      <- NumGens.genPosInt(minLength, PosInt(300)).log("maxLength")
    nonEmptyString <- StringGens.genNonEmptyStringMinMax(Gen.unicodeAll, minLength, maxLength).log("nonEmptyString")
  } yield {
    Result.all(
      List(
        Result
          .assert(nonEmptyString.value.nonEmpty)
          .log(
            s"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty.toString}"
          ),
        Result.diffNamed(
          s"The length of monEmptyString should be ${minLength.value.toString} <= length <= ${maxLength.value.toString}",
          minLength.value to maxLength.value,
          nonEmptyString.value.length
        )((range, actual) => range.exists(_ == actual))
      )
    )
  }

  def testGenNonBlankString: Property = for {
    maxLength      <- NumGens.genPosIntMaxTo(PosInt(300)).log("maxLength")
    nonBlankString <- StringGens.genNonBlankString(maxLength).log("nonBlankString")
  } yield {
    (nonBlankString.value.exists(_.isWhitespace) ==== false)
      .log(
        "nonBlankString should not contain any whitespace char but it has. " +
          show"'$nonBlankString' " +
          show"(${nonBlankString.value.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
          show"${nonBlankString.value.map(_.toInt).mkString("[", ",", "]")})"
      )
  }

  def testGenNonBlankStringMinMax: Property = for {
    minLength <- NumGens.genPosIntMaxTo(PosInt(10)).log("minLength")
    maxLength <- NumGens.genPosInt(minLength, PosInt(300)).log("maxLength")

    nonBlankString <- StringGens.genNonBlankStringMinMax(minLength, maxLength).log("nonBlankString")
  } yield {
    Result.all(
      List(
        (nonBlankString.value.exists(_.isWhitespace) ==== false)
          .log(
            "nonBlankString should not contain any whitespace char but it has. " +
              show"'$nonBlankString' " +
              show"(${nonBlankString.value.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
              show"${nonBlankString.value.map(_.toInt).mkString("[", ",", "]")})"
          ),
        Result.diffNamed(
          show"The length of nonBlankString should be $minLength <= length <= $maxLength",
          minLength.value to maxLength.value,
          nonBlankString.value.length
        )((range, actual) => range.exists(_ === actual))
      )
    )
  }

  def testGenUuid: Property = for {
    uuid <- StringGens.genUuid.log("uuid")
  } yield {
    val expected = java.util.UUID.fromString(uuid.value)
    val actual   = uuid.toUUID
    actual ==== expected
  }

}
