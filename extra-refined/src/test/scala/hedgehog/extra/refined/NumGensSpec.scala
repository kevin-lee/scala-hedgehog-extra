package hedgehog.extra.refined

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-01-02
  */
object NumGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("testGenNegativeInt", testGenNegativeInt),
    property("testGenNonPositiveInt", testGenNonPositiveInt),
    property("testGenPositiveInt", testGenPositiveInt),
    property("testGenNonNegativeInt", testGenNonNegativeInt),
    property("testGenNegativeLong", testGenNegativeLong),
    property("testGenNonPositiveLong", testGenNonPositiveLong),
    property("testGenPositiveLong", testGenPositiveLong),
    property("testGenNonNegativeLong", testGenNonNegativeLong)
  )

  def testGenNegativeInt: Property = for {
    n <- NumGens.genNegativeInt(NegativeInt.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveInt: Property = for {
    n <- NumGens.genNonPositiveInt(NonPositiveInt.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveInt: Property = for {
    n <- NumGens.genPositiveInt(PositiveInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeInt: Property = for {
    n <- NumGens.genNonNegativeInt(NonNegativeInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNegativeLong: Property = for {
    n <- NumGens.genNegativeLong(NegativeLong.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveLong: Property = for {
    n <- NumGens.genNonPositiveLong(NonPositiveLong.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveLong: Property = for {
    n <- NumGens.genPositiveLong(PositiveLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0L)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeLong: Property = for {
    n <- NumGens.genNonNegativeLong(NonNegativeLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0L)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

}
