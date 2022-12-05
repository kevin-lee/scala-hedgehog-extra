package hedgehog.extra.refined

import hedgehog._
import hedgehog.runner._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric._

/** @author Kevin Lee
  * @since 2021-01-02
  */
object NumGensSpec extends Properties {
  override def tests: List[Test] = List(
    property("testGenNegativeIntWithMinMax", testGenNegativeIntWithMinMax),
    property("testGenNegativeInt", testGenNegativeInt),
    property("testGenNonPositiveIntWithMinMax", testGenNonPositiveIntWithMinMax),
    property("testGenNonPositiveInt", testGenNonPositiveInt),
    property("testGenPositiveIntWithMinMax", testGenPositiveIntWithMinMax),
    property("testGenPositiveInt", testGenPositiveInt),
    property("testGenNonNegativeIntWithMinMax", testGenNonNegativeIntWithMinMax),
    property("testGenNonNegativeInt", testGenNonNegativeInt),
  ) ++ List(
    property("testGenNegativeLongWithMinMax", testGenNegativeLongWithMinMax),
    property("testGenNegativeLong", testGenNegativeLong),
    property("testGenNonPositiveLongWithMinMax", testGenNonPositiveLongWithMinMax),
    property("testGenNonPositiveLong", testGenNonPositiveLong),
    property("testGenPositiveLongWithMinMax", testGenPositiveLongWithMinMax),
    property("testGenPositiveLong", testGenPositiveLong),
    property("testGenNonNegativeLongWithMinMax", testGenNonNegativeLongWithMinMax),
    property("testGenNonNegativeLong", testGenNonNegativeLong),
  ) ++ List(
    property("testGenNegDoubleWithMinMax", testGenNegDoubleWithMinMax),
    property("testGenNegDouble", testGenNegDouble),
    property("testGenNonPosDoubleWithMinMax", testGenNonPosDoubleWithMinMax),
    property("testGenNonPosDouble", testGenNonPosDouble),
    property("testGenPosDoubleWithMinMax", testGenPosDoubleWithMinMax),
    property("testGenPosDouble", testGenPosDouble),
    property("testGenNonNegDoubleWithMinMax", testGenNonNegDoubleWithMinMax),
    property("testGenNonNegDouble", testGenNonNegDouble),
  )

  def testGenNegativeIntWithMinMax: Property = for {
    n <- NumGens.genNegInt(NegInt.MinValue, NegInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNegativeInt: Property = for {
    n <- NumGens.genNegInt(NegInt.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveIntWithMinMax: Property = for {
    n <- NumGens.genNonPosInt(NonPosInt.MinValue, NonPosInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveInt: Property = for {
    n <- NumGens.genNonPosInt(NonPosInt.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveIntWithMinMax: Property = for {
    n <- NumGens.genPosInt(PosInt.MinValue, PosInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveInt: Property = for {
    n <- NumGens.genPosInt(PosInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeIntWithMinMax: Property = for {
    n <- NumGens.genNonNegInt(NonNegInt.MinValue, NonNegInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeInt: Property = for {
    n <- NumGens.genNonNegInt(NonNegInt.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNegativeLongWithMinMax: Property = for {
    n <- NumGens.genNegLong(NegLong.MinValue, NegLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNegativeLong: Property = for {
    n <- NumGens.genNegLong(NegLong.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveLongWithMinMax: Property = for {
    n <- NumGens.genNonPosLong(NonPosLong.MinValue, NonPosLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonPositiveLong: Property = for {
    n <- NumGens.genNonPosLong(NonPosLong.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0L)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveLongWithMinMax: Property = for {
    n <- NumGens.genPosLong(PosLong.MinValue, PosLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0L)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenPositiveLong: Property = for {
    n <- NumGens.genPosLong(PosLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0L)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeLongWithMinMax: Property = for {
    n <- NumGens.genNonNegLong(NonNegLong.MinValue, NonNegLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0L)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegativeLong: Property = for {
    n <- NumGens.genNonNegLong(NonNegLong.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0L)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  ///

  def testGenNegDoubleWithMinMax: Property = for {
    n <- NumGens.genNegDouble(NegDouble.MinValue, -0.00000000000000000001d).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNegDouble: Property = for {
    n <- NumGens.genNegDouble(NegDouble.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value < _),
        ((n.value >= 0) ==== false).log(s"n should not be greater than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonPosDoubleWithMinMax: Property = for {
    n <- NumGens.genNonPosDouble(NonPosDouble.MinValue, NonPosDouble.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonPosDouble: Property = for {
    n <- NumGens.genNonPosDouble(NonPosDouble.MinValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be less than 0", n, 0)(_.value <= _),
        ((n.value > 0) ==== false).log(s"n should not be greater than zero. n: ${n.toString}")
      )
    )
  }

  def testGenPosDoubleWithMinMax: Property = for {
    n <- NumGens.genPosDouble(PosDouble.MinValue, PosDouble.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenPosDouble: Property = for {
    n <- NumGens.genPosDouble(PosDouble.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than 0", n, 0)(_.value > _),
        ((n.value <= 0) ==== false).log(s"n should not be less than or equal to 0. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegDoubleWithMinMax: Property = for {
    n <- NumGens.genNonNegDouble(NonNegDouble.MinValue, NonNegDouble.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

  def testGenNonNegDouble: Property = for {
    n <- NumGens.genNonNegDouble(NonNegDouble.MaxValue).log("n")
  } yield {
    Result.all(
      List(
        Result.diffNamed("n should be greater than or equal to 0", n, 0)(_.value >= _),
        ((n.value < 0) ==== false).log(s"n should not be less than zero. n: ${n.toString}")
      )
    )
  }

}
