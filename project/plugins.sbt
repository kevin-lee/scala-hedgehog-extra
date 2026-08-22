logLevel := sbt.Level.Warn

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.12.1")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.4.4")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.15")

addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.14.7")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.6.2")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.6.1")

addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.20.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.4.0")

addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.5.10")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.3.2")

val SbtDevOopsVersion = "3.8.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % SbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % SbtDevOopsVersion)

addSbtPlugin("com.eed3si9n" % "sbt-salad-days" % "0.2.0")

addSbtPlugin("org.scalameta" % "sbt-mdoc"     % "2.9.1")
addSbtPlugin("io.kevinlee"   % "sbt-docusaur" % "0.22.0")
