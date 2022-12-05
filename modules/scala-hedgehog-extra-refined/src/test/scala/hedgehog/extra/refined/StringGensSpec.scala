package hedgehog.extra.refined

import eu.timepit.refined.types.numeric.PosInt
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-04-07
  */
object StringGensSpec extends Properties {

  override def tests: List[Test] = List(
    property("testGenNonWhitespaceString", testGenNonWhitespaceString).withTests(10000)
  )

  def testGenNonWhitespaceString: Property = for {
    maxLength           <- NumGens.genPosInt(PosInt(300)).log("maxLength")
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

}
