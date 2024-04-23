package hedgehog.extra

import hedgehog._

import java.time.{Instant, LocalDate}
import scala.concurrent.duration.FiniteDuration

/** @author Kevin Lee
  * @since 2024-02-21
  */
object TimeGens {
  def genInstant(from: Instant, to: Instant): Gen[Instant] = {
    val fromMillis = from.toEpochMilli
    val toMillis   = to.toEpochMilli
    Gen.long(Range.linear(fromMillis, toMillis)).map(Instant.ofEpochMilli)
  }

  def genInstantFrom(baseInstant: Instant, durationAgo: FiniteDuration, durationAfter: FiniteDuration): Gen[Instant] = {
    val from        = baseInstant.minusMillis(durationAgo.toMillis)
    val fromSeconds = from.getEpochSecond
    val fromNanos   = from.getNano

    val to        = baseInstant.plusMillis(durationAfter.toMillis)
    val toSeconds = to.getEpochSecond
    val toNanos   = to.getNano

    Gen
      .long(Range.linear(fromSeconds * 1000000000 + fromNanos, toSeconds * 1000000000 + toNanos))
      .map(l => Instant.ofEpochSecond(l / 1000000000L, l % 1000000000))
//      .long(Range.linear(fromSeconds * 1_000_000_000 + fromNanos, toSeconds * 1_000_000_000 + toNanos))
//      .map(l => Instant.ofEpochSecond(l / 1_000_000_000L, l % 1_000_000_000))
  }

  def genLocalDate(from: LocalDate, to: LocalDate): Gen[LocalDate] = {
    val fromEpochDay = from.toEpochDay
    val toEpochDay   = to.toEpochDay
    Gen.long(Range.linear(fromEpochDay, toEpochDay)).map(LocalDate.ofEpochDay)
  }

}
