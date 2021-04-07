package hedgehog.extra

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-04-07
  */
object GensSpec extends Properties {

  override def tests: List[Test] = List(
    property("testGenNonWhitespaceChar", testGenNonWhitespaceChar).withTests(10000)
  )

  def testGenNonWhitespaceChar: Property = for {
    nonWhitespaceChar <- Gens.genNonWhitespaceChar.log("nonWhitespaceChar")
  } yield {
    (nonWhitespaceChar.isWhitespace ==== false)
      .log(s"nonSpaceChar should be non-whitespace char but it was. '${nonWhitespaceChar.toString}' (${"\\u%04x"
        .format(nonWhitespaceChar.toInt)} / ${nonWhitespaceChar.toInt.toString})")
  }

}
