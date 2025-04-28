logLevel := sbt.Level.Warn

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.9.3")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.3.1")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.15")

addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.14.2")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.5.4")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.3.3")

addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.16.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.2")

val SbtDevOopsVersion = "3.1.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % SbtDevOopsVersion)

addSbtPlugin("org.scalameta" % "sbt-mdoc"     % "2.3.6")
addSbtPlugin("io.kevinlee"   % "sbt-docusaur" % "0.12.0")
