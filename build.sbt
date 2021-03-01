import SbtProjectInfo.{ProjectName, commonWarts}
import just.semver.SemVer

lazy val props =
  new {
    val GitHubUsername = "Kevin-Lee"
    val RepoName       = "scala-hedgehog-extra"
    val Org            = "io.kevinlee"

    val ProjectScalaVersion = "2.13.3"
    val CrossScalaVersions  = Seq("2.11.12", "2.12.13", ProjectScalaVersion, "3.0.0-RC1")

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val hedgehogVersion = "0.6.5"

    val IncludeTest: String = "compile->compile;test->test"
  }

lazy val libs =
  new {
    val hedgehogLibs: Seq[ModuleID] = Seq(
      "qa.hedgehog" %% "hedgehog-core"   % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.hedgehogVersion,
    )
  }

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / version := SbtProjectInfo.ProjectVersion
ThisBuild / organization := props.Org
ThisBuild / organizationName := "Kevin's Code"
ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}"),
  )
)
ThisBuild / testFrameworks ++= Seq(TestFramework("hedgehog.sbt.Framework"))

lazy val hedgehogExtra = Project("hedgehog-extra", file("."))
  .settings(
    libraryDependencies := removeDottyIncompatible(isDotty.value, libraryDependencies.value)
  )
  .aggregate(extraCore, extraRefined)

lazy val extraCore = subProject("extra-core", ProjectName("core"), file("extra-core"))
  .settings(
    libraryDependencies := removeDottyIncompatible(isDotty.value, libraryDependencies.value)
  )

lazy val extraRefined = subProject("extra-refined", ProjectName("refined"), file("extra-refined"))
  .settings(
    libraryDependencies ++= (SemVer.parseUnsafe(scalaVersion.value) match {
      case SemVer(SemVer.Major(2), SemVer.Minor(11), _, _, _) =>
        Seq("eu.timepit" %% "refined" % "0.9.12")
      case _                                                  =>
        Seq("eu.timepit" %% "refined" % "0.9.21")
    }),
    libraryDependencies := removeDottyIncompatible(isDotty.value, libraryDependencies.value),
  )

def removeDottyIncompatible(isDotty: Boolean, libraries: Seq[ModuleID]): Seq[ModuleID] =
  if (isDotty)
    libraries.filterNot(props.removeDottyIncompatible)
  else
    libraries

//lazy val extra =

// format: off
def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// format: on

def scalacOptionsPostProcess(scalaSemVer: SemVer, isDotty: Boolean, options: Seq[String]): Seq[String] =
  if (isDotty || (scalaSemVer.major, scalaSemVer.minor) == (SemVer.Major(3), SemVer.Minor(0))) {
    Seq(
      "-source:3.0-migration",
      "-language:" + List(
        "dynamics",
        "existentials",
        "higherKinds",
        "reflectiveCalls",
        "experimental.macros",
        "implicitConversions",
      ).mkString(","),
      "-Ykind-projector",
      "-siteroot",
      "./dotty-docs",
    )
  } else {
    scalaSemVer match {
      case SemVer(SemVer.Major(2), SemVer.Minor(13), SemVer.Patch(patch), _, _) =>
        ((if (patch >= 3) {
            options.distinct.filterNot(_ == "-Xlint:nullary-override")
          } else {
            options.distinct
          }) ++ Seq("-Ymacro-annotations", "-language:implicitConversions")).distinct
      case _: SemVer                                                            =>
        options.distinct
    }
  }

def subProject(id: String, projectName: ProjectName, file: File): Project =
  Project(id, file)
    .settings(
      name := prefixedProjectName(projectName.projectName),
      crossScalaVersions := props.CrossScalaVersions,
      addCompilerPlugin("org.typelevel" % "kind-projector"     % "0.11.3" cross CrossVersion.full),
      addCompilerPlugin("com.olegpy"   %% "better-monadic-for" % "0.3.1"),
      scalacOptions := scalacOptionsPostProcess(
        SemVer.parseUnsafe(scalaVersion.value),
        isDotty.value,
        scalacOptions.value,
      ),
      testFrameworks ++= Seq(TestFramework("hedgehog.sbt.Framework")),
      resolvers ++= Seq(
        Resolver.sonatypeRepo("releases")
      ),
      libraryDependencies ++=
        libs.hedgehogLibs,
      /* WartRemover and scalacOptions { */
      wartremoverErrors in (Compile, compile) ++= commonWarts((scalaBinaryVersion in update).value),
      wartremoverErrors in (Test, compile) ++= commonWarts((scalaBinaryVersion in update).value),
//      wartremoverErrors ++= commonWarts((scalaBinaryVersion in update).value)
      //      , wartremoverErrors ++= Warts.all
      Compile / console / wartremoverErrors := List.empty,
      Compile / console / wartremoverWarnings := List.empty,
      Compile / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .distinct
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      Test / console / wartremoverErrors := List.empty,
      Test / console / wartremoverWarnings := List.empty,
      Test / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .distinct
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      /* } WartRemover and scalacOptions */
      testFrameworks ++= Seq(TestFramework("hedgehog.sbt.Framework")),
      /* Ammonite-REPL { */
      libraryDependencies ++=
        (scalaBinaryVersion.value match {
          case "2.10" =>
            Seq.empty[ModuleID]
          case "2.11" =>
            Seq("com.lihaoyi" % "ammonite" % "1.6.7" % Test cross CrossVersion.full)
          case "2.12" =>
            Seq("com.lihaoyi" % "ammonite" % "2.3.8-36-1cce53f3" % Test cross CrossVersion.full)
          case "2.13" =>
            Seq("com.lihaoyi" % "ammonite" % "2.3.8-36-1cce53f3" % Test cross CrossVersion.full)
          case _      =>
            Seq.empty[ModuleID]
        }),
      unmanagedSourceDirectories in Compile ++= {
        val sharedSourceDir = baseDirectory.value / "src/main"
        if (scalaVersion.value.startsWith("3.0"))
          Seq(
            sharedSourceDir / "scala-2.12_3.0",
            sharedSourceDir / "scala-2.13_3.0",
          )
        else if (scalaVersion.value.startsWith("2.13"))
          Seq(
            sharedSourceDir / "scala-2.12_2.13",
            sharedSourceDir / "scala-2.12_3.0",
            sharedSourceDir / "scala-2.13_3.0",
          )
        else if (scalaVersion.value.startsWith("2.12"))
          Seq(
            sharedSourceDir / "scala-2.12_2.13",
            sharedSourceDir / "scala-2.12_3.0",
            sharedSourceDir / "scala-2.11_2.12",
          )
        else if (scalaVersion.value.startsWith("2.11"))
          Seq(sharedSourceDir / "scala-2.11_2.12")
        else
          Seq.empty
      },
      unmanagedSourceDirectories in Test ++= {
        val sharedSourceDir = baseDirectory.value / "src/test"
        if (scalaVersion.value.startsWith("3.0"))
          Seq(
            sharedSourceDir / "scala-2.12_3.0",
            sharedSourceDir / "scala-2.13_3.0",
          )
        else if (scalaVersion.value.startsWith("2.13"))
          Seq(
            sharedSourceDir / "scala-2.12_2.13",
            sharedSourceDir / "scala-2.13_3.0",
          )
        else if (scalaVersion.value.startsWith("2.12"))
          Seq(
            sharedSourceDir / "scala-2.12_2.13",
            sharedSourceDir / "scala-2.12_3.0",
            sharedSourceDir / "scala-2.11_2.12",
          )
        else if (scalaVersion.value.startsWith("2.11"))
          Seq(sharedSourceDir / "scala-2.11_2.12")
        else
          Seq.empty
      },
      sourceGenerators in Test +=
        (scalaBinaryVersion.value match {
          case "2.10"          =>
            task(Seq.empty[File])
          case "2.12" | "2.13" =>
            task {
              val file = (sourceManaged in Test).value / "amm.scala"
              IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
              Seq(file)
            }
          case _               =>
            task(Seq.empty[File])
        }),
      /* } Ammonite-REPL */

    )
