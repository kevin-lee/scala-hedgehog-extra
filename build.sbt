import SbtProjectInfo.{ProjectName, commonWarts}
import just.semver.SemVer

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / organization := props.Org
ThisBuild / organizationName := "Kevin's Code"
ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}")
  )
)

ThisBuild / homepage := Some(url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"))
ThisBuild / scmInfo :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
      s"git@github.com:${props.GitHubUsername}/${props.RepoName}.git",
    )
  )

ThisBuild / licenses := props.Licenses
ThisBuild / resolvers += "sonatype-snapshots" at s"https://${props.SonatypeCredentialHost}/content/repositories/snapshots"

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3")) file(".scalafix-scala3.conf").some
  else file(".scalafix-scala2.conf").some
)

ThisBuild / scalafixScalaBinaryVersion := {
  val log        = sLog.value
  val newVersion = if (scalaVersion.value.startsWith("3")) {
    (ThisBuild / scalafixScalaBinaryVersion).value
  } else {
    CrossVersion.binaryScalaVersion(scalaVersion.value)
  }

  log.info(
    s">> Change ThisBuild / scalafixScalaBinaryVersion from ${(ThisBuild / scalafixScalaBinaryVersion).value} to $newVersion"
  )
  newVersion
}

ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % "0.2.15"

lazy val hedgehogExtra = Project(props.ProjectName, file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .settings(mavenCentralPublishSettings)
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(
    extraCore,
    extraRefined,
    extraRefined4s,
  )

lazy val extraCore = subProject(ProjectName("core"))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehogLibs ++ libs.hedgehogLibsForTesting,
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )

lazy val extraRefined = subProject(ProjectName("refined"))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehogLibs ++ libs.hedgehogLibsForTesting,
    libraryDependencies ++= (SemVer.parseUnsafe(scalaVersion.value) match {
      case SemVer(SemVer.Major(3), SemVer.Minor(mn), _, _, _) if mn >= 2 =>
        Seq("eu.timepit" %% "refined" % "0.10.2")
      case SemVer(SemVer.Major(3), SemVer.Minor(1), _, _, _) =>
        Seq("eu.timepit" %% "refined" % "0.10.1")
      case SemVer(SemVer.Major(2), SemVer.Minor(11), _, _, _) =>
        Seq("eu.timepit" %% "refined" % "0.9.12" excludeAll ("org.scala-lang.modules" %% "scala-xml"))
      case _ =>
        Seq("eu.timepit" %% "refined" % "0.9.27" excludeAll ("org.scala-lang.modules" %% "scala-xml"))
    }),
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .dependsOn(extraCore)

lazy val extraRefined4s = subProject(ProjectName("refined4s"))
  .settings(
    scalaVersion := props.Scala3Version,
    crossScalaVersions := Seq.empty,
    libraryDependencies ++=
      (SemVer.parseUnsafe(scalaVersion.value) match {
        case SemVer(SemVer.Major(3), SemVer.Minor(mn), _, _, _) if mn >= 1 =>
          Seq(
            libs.refined4sCore,
            libs.refined4sCats % Test,
          )
        case _ =>
          Seq.empty
      }),
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .dependsOn(extraCore % props.IncludeTest)

lazy val props =
  new {
    val Org = "io.kevinlee"

    val GitHubUsername = "Kevin-Lee"

    val ProjectName = "hedgehog-extra"
    val RepoName    = "scala-" + ProjectOrigin

    val Scala2Version = "2.13.6"
    val Scala3Version = "3.1.3"

    val ProjectScalaVersion = Scala3Version
//    val ProjectScalaVersion = Scala2Version
    val CrossScalaVersions  =
      Seq(
        Scala3Version,
        Scala2Version,
        "2.12.13",
        "2.11.12",
      ).distinct

    val Licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

    val SonatypeCredentialHost = "s01.oss.sonatype.org"
    val SonatypeRepository     = s"https://$SonatypeCredentialHost/service/local"

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
//          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val HedgehogVersion = "0.10.1"

    val Refined4sVersion = "0.11.0"

    val IncludeTest = "compile->compile;test->test"

    val isScala3IncompatibleScalacOption: String => Boolean =
      _.startsWith("-P:wartremover")

  }

lazy val libs =
  new {
    lazy val hedgehogLibs = Seq(
      "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion
    )

    lazy val refined4sCore = "io.kevinlee" %% "refined4s-core" % props.Refined4sVersion
    lazy val refined4sCats = "io.kevinlee" %% "refined4s-cats" % props.Refined4sVersion

    lazy val hedgehogLibsForTesting =
      Seq(
        "qa.hedgehog" %% "hedgehog-sbt" % props.HedgehogVersion
      ).map(_ % Test)
  }

def removeDottyIncompatible(isScala3: Boolean, libraries: Seq[ModuleID]): Seq[ModuleID] =
  if (isScala3)
    libraries.filterNot(props.removeDottyIncompatible).distinct
  else
    libraries.distinct

lazy val mavenCentralPublishSettings: SettingsDefinition = List(
  /* Publish to Maven Central { */
  sonatypeCredentialHost := props.SonatypeCredentialHost,
  sonatypeRepository := props.SonatypeRepository,
  /* } Publish to Maven Central */
)

// format: off
def prefixedProjectName(name: String) = s"${props.ProjectName}${if (name.isEmpty) "" else s"-$name"}"
// format: on

def subProject(projectName: ProjectName): Project = {
  val prefixedName = prefixedProjectName(projectName.projectName)
  Project(prefixedName, file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
      /* WartRemover and scalacOptions { */
//      Compile / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value),
//      Test / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value),
      wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value),
      //      wartremoverErrors ++= commonWarts((scalaBinaryVersion in update).value)
      //      , wartremoverErrors ++= Warts.all
//      Compile / console / wartremoverErrors := List.empty,
//      Compile / console / wartremoverWarnings := List.empty,
      Compile / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .distinct
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
//      Test / console / wartremoverErrors := List.empty,
//      Test / console / wartremoverWarnings := List.empty,
      Test / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .distinct
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      /* } WartRemover and scalacOptions */
      /* Ammonite-REPL { */
      scalacOptions ~= (_.filterNot(props.isScala3IncompatibleScalacOption)),
      libraryDependencies ++= {
        scalaBinaryVersion.value match {
          case "2.10" =>
            Seq("com.lihaoyi" % "ammonite" % "1.0.3" % Test cross CrossVersion.full)
          case "2.11" =>
            Seq("com.lihaoyi" % "ammonite" % "1.6.7" % Test cross CrossVersion.full)
          case "3" =>
            Seq.empty
          case _ =>
            Seq("com.lihaoyi" % "ammonite" % "3.0.0-M0" % Test cross CrossVersion.full)
        }
      },
      Test / sourceGenerators +=
        (scalaBinaryVersion.value match {
          case "2.10" | "2.11" =>
            task(Seq.empty[File])
//          case "2.12" | "2.13" | "3" =>
          case "2.12" | "2.13" =>
            task {
              val file = (Test / sourceManaged).value / "amm.scala"
              IO.write(
                file,
                """object amm extends App { ammonite.AmmoniteMain.main(args) }"""
              )
              Seq(file)
            }
          case _ =>
            task(Seq.empty[File])
        })
      /* } Ammonite-REPL */

    )
    .settings(mavenCentralPublishSettings)

}

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")
