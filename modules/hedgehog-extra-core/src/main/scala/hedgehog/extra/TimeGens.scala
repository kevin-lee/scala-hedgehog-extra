package hedgehog.extra

import hedgehog._

import java.time.Instant

/** @author Kevin Lee
  * @since 2024-02-21
  */
object TimeGens {
  def genInstant(from: Instant, to: Instant): Gen[Instant] = {
    val fromMillis = from.toEpochMilli
    val toMillis   = to.toEpochMilli
    Gen.long(Range.linear(fromMillis, toMillis)).map(Instant.ofEpochMilli)
  }
}
