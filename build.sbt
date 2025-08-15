import sbtcrossproject.CrossProject
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

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3")) file(".scalafix-scala3.conf").some
  else file(".scalafix-scala2.conf").some
)

ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % "0.2.15"

lazy val hedgehogExtra = Project(props.ProjectName, file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(
    extraCoreJvm,
    extraCoreJs,
    extraRefinedJvm,
    extraRefinedJs,
    extraRefined4sJvm,
    extraRefined4sJs,
  )

lazy val extraCore       = subProject(ProjectName("core"), crossProject(JVMPlatform, JSPlatform, NativePlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehogLibs.value ++ libs.hedgehogLibsForTesting.value,
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
lazy val extraCoreJvm    = extraCore.jvm
lazy val extraCoreJs     = extraCore.js.settings(jsSettingsForFuture)
lazy val extraCoreNative = extraCore.native.settings(nativeSettings)

lazy val extraRefined    = subProject(ProjectName("refined"), crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions.filter(!_.startsWith("2.11")),
    libraryDependencies ++= libs.hedgehogLibs.value ++ libs.hedgehogLibsForTesting.value,
    libraryDependencies ++= (SemVer.parseUnsafe(scalaVersion.value) match {
      case SemVer(SemVer.Major(3), SemVer.Minor(mn), _, _, _) if mn >= 2 =>
        Seq("eu.timepit" %%% "refined" % "0.10.2")
      case SemVer(SemVer.Major(3), SemVer.Minor(1), _, _, _) =>
        Seq("eu.timepit" %%% "refined" % "0.10.1")
//      case SemVer(SemVer.Major(2), SemVer.Minor(11), _, _, _) =>
//        Seq("eu.timepit" %% "refined" % "0.9.12" excludeAll ("org.scala-lang.modules" %% "scala-xml"))
      case _ =>
        Seq("eu.timepit" %%% "refined" % "0.9.27" excludeAll ("org.scala-lang.modules" %% "scala-xml"))
    }),
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .dependsOn(extraCore)
lazy val extraRefinedJvm = extraRefined.jvm
lazy val extraRefinedJs  = extraRefined.js.settings(jsSettingsForFuture)

lazy val extraRefined4s    = subProject(ProjectName("refined4s"), crossProject(JVMPlatform, JSPlatform))
  .settings(
    scalaVersion := props.Scala3Version,
    crossScalaVersions := Seq.empty,
    libraryDependencies ++=
      (SemVer.parseUnsafe(scalaVersion.value) match {
        case SemVer(SemVer.Major(3), SemVer.Minor(mn), _, _, _) if mn >= 1 =>
          Seq(
            libs.refined4sCore.value,
            libs.refined4sCats.value % Test,
          )
        case _ =>
          Seq.empty
      }),
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .dependsOn(extraCore % props.IncludeTest)
lazy val extraRefined4sJvm = extraRefined4s.jvm
lazy val extraRefined4sJs  = extraRefined4s.js.settings(jsSettingsForFuture)

lazy val props =
  new {
    val Org = "io.kevinlee"

    val GitHubUsername = "kevin-lee"

    val ProjectName = "hedgehog-extra"
    val RepoName    = "scala-" + ProjectName

    val Scala2Version = "2.13.16"
    val Scala3Version = "3.3.4"

    val ProjectScalaVersion = Scala3Version
//    val ProjectScalaVersion = Scala2Version
    val CrossScalaVersions  =
      Seq(
        Scala3Version,
        Scala2Version,
        "2.12.18",
      ).distinct

    val Licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
//          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val HedgehogVersion = "0.13.0"

    val Refined4sVersion = "1.4.0"

    val IncludeTest = "compile->compile;test->test"

    val isScala3IncompatibleScalacOption: String => Boolean =
      _.startsWith("-P:wartremover")

  }

lazy val libs =
  new {
    lazy val hedgehogLibs = Def.setting(
      Seq(
        "qa.hedgehog" %%% "hedgehog-core"   % props.HedgehogVersion,
        "qa.hedgehog" %%% "hedgehog-runner" % props.HedgehogVersion
      )
    )

    lazy val refined4sCore = Def.setting {
      "io.kevinlee" %%% "refined4s-core" % props.Refined4sVersion
    }
    lazy val refined4sCats = Def.setting {
      "io.kevinlee" %%% "refined4s-cats" % props.Refined4sVersion
    }

    lazy val hedgehogLibsForTesting =
      Def.setting(
        Seq(
          "qa.hedgehog" %%% "hedgehog-sbt" % props.HedgehogVersion
        ).map(_ % Test)
      )
  }

def removeDottyIncompatible(isScala3: Boolean, libraries: Seq[ModuleID]): Seq[ModuleID] =
  if (isScala3)
    libraries.filterNot(props.removeDottyIncompatible).distinct
  else
    libraries.distinct

// format: off
def prefixedProjectName(name: String) = s"${props.ProjectName}${if (name.isEmpty) "" else s"-$name"}"
// format: on

def subProject(projectName: ProjectName, crossProject: CrossProject.Builder): CrossProject = {
  val prefixedName = prefixedProjectName(projectName.projectName)
  crossProject
    .in(file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      fork := true,
      semanticdbEnabled := true,
//      semanticdbVersion := scalafixSemanticdb.revision,
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
            Seq("com.lihaoyi" % "ammonite" % "3.0.2" % Test cross CrossVersion.full)
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

}

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")

lazy val jsSettingsForFuture: SettingsDefinition = List(
  Test / fork := false,
  Test / scalacOptions ++= (if (scalaVersion.value.startsWith("3")) List.empty
                            else List("-P:scalajs:nowarnGlobalExecutionContext")),
  Test / compile / scalacOptions ++= (if (scalaVersion.value.startsWith("3")) List.empty
                                      else List("-P:scalajs:nowarnGlobalExecutionContext")),
  coverageEnabled := false,
)

lazy val nativeSettings: SettingsDefinition = List(
  Test / fork := false
)
