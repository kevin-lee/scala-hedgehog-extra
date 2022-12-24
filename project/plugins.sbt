logLevel := sbt.Level.Warn

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.6")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.2")

addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.10.4")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.4.6")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.13")

val SbtDevOopsVersion = "2.23.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % SbtDevOopsVersion)

addSbtPlugin("org.scalameta" % "sbt-mdoc"     % "2.3.6")
addSbtPlugin("io.kevinlee"   % "sbt-docusaur" % "0.12.0")
