package hedgehog.extra.refined4s.gens

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2022-12-11
  */
object NetworkGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("test NetworkGens.genPortNumber", testGenPortNumber).withTests(70000),
    property("test NetworkGens.genSystemPortNumber", testGenSystemPortNumber).withTests(70000),
    property("test NetworkGens.genUserPortNumber", testGenUserPortNumber).withTests(70000),
    property("test NetworkGens.genDynamicPortNumber", testGenDynamicPortNumber).withTests(70000),
    property("test NetworkGens.genNonSystemPortNumber", testGenNonSystemPortNumber).withTests(70000)
  )

  private def testGenPortNumber: Property =
    NetworkGens
      .genPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenSystemPortNumber: Property =
    NetworkGens
      .genSystemPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenUserPortNumber: Property =
    NetworkGens
      .genUserPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenDynamicPortNumber: Property =
    NetworkGens
      .genDynamicPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenNonSystemPortNumber: Property =
    NetworkGens
      .genNonSystemPortNumber
      .forAll
      .map(_ => Result.success)

}
