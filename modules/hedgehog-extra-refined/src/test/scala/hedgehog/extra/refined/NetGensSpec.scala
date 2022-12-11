package hedgehog.extra.refined

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-12-11
  */
object NetGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("test NetGens.genPortNumber", testGenPortNumber).withTests(70000),
    property("test NetGens.genSystemPortNumber", testGenSystemPortNumber).withTests(70000),
    property("test NetGens.genUserPortNumber", testGenUserPortNumber).withTests(70000),
    property("test NetGens.genDynamicPortNumber", testGenDynamicPortNumber).withTests(70000),
    property("test NetGens.genNonSystemPortNumber", testGenNonSystemPortNumber).withTests(70000)
  )

  private def testGenPortNumber: Property =
    NetGens
      .genPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenSystemPortNumber: Property =
    NetGens
      .genSystemPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenUserPortNumber: Property =
    NetGens
      .genUserPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenDynamicPortNumber: Property =
    NetGens
      .genDynamicPortNumber
      .forAll
      .map(_ => Result.success)

  private def testGenNonSystemPortNumber: Property =
    NetGens
      .genNonSystemPortNumber
      .forAll
      .map(_ => Result.success)

}
