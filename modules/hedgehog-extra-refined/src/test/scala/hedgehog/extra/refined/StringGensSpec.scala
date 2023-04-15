package hedgehog.extra.refined

import eu.timepit.refined.types.numeric.PosInt
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-04-07
  */
object StringGensSpec extends Properties {

  override def tests: List[Test] = List(
    property("test StringGens.genNonWhitespaceString", testGenNonWhitespaceString).withTests(10000),
    property("test StringGens.genNonEmptyString with Gen.alphaNum", testGenNonEmptyStringAlphaNum).withTests(10000),
    property("test StringGens.genNonEmptyString with Gen.unicode", testGenNonEmptyStringUnicode).withTests(10000)
  )

  def testGenNonWhitespaceString: Property = for {
    maxLength           <- NumGens.genPosIntMaxTo(PosInt(300)).log("maxLength")
    nonWhitespaceString <- StringGens.genNonWhitespaceString(maxLength).log("nonWhitespaceString")
  } yield {
    (nonWhitespaceString.value.exists(_.isWhitespace) ==== false)
      .log(
        s"nonWhitespaceString should not contain any whitespace char but it has. " +
          s"'${nonWhitespaceString.value}' " +
          s"(${nonWhitespaceString.value.map(c => "\\u%04x".format(c.toInt)).mkString} / " +
          s"${nonWhitespaceString.value.map(_.toInt).mkString("[", ",", "]")})"
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
            s"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty.toString}"
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
        s"nonEmptyString should not be empty. nonEmptyString.value.nonEmpty: ${nonEmptyString.value.nonEmpty.toString}"
      )
  }

}
