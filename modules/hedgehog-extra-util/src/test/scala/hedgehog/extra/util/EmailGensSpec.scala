package hedgehog.extra.util

import com.sanctionco.jmail.JMail
import hedgehog._
import hedgehog.extra.util.EmailGens._
import hedgehog.runner._

/** @author Cyril Chen
  * @since 2023-01-26
  */
object EmailGensSpec extends Properties {
  override def tests: List[Prop] = List(
    property("test genDefaultRandomEmail", testGenDefaultRandomEmail)
  )

  def testGenDefaultRandomEmail: Property =
    for {
      email <- genDefaultRandomEmail.log("email")
    } yield Result.assert(JMail.isValid(email))
}
