package hedgehog.extra.util

import hedgehog._

/** @author Cyril Chen
  * @since 2023-02-26
  */
trait EmailGens {
  val defaultDomains: List[String] = List("gmail", "yahoo", "hotmail", "outlook")
  val defaultTld: List[String]     = List("com", "net", "org")

  // todo customise domain
  def addDomains(domains: String*): List[String] = defaultDomains ++ domains

  def genDefaultRandomEmail: Gen[String] =
    for {
      name   <- Gen.string(Gen.alphaNum, Range.linear(1, 10))
      domain <- Gen.element1("gmail", defaultDomains)
      tld    <- Gen.element1("com", defaultTld)
    } yield s"$name@$domain.$tld"
}

object EmailGens extends EmailGens
