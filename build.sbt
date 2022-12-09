import SbtProjectInfo.{ProjectName, commonWarts}
import just.semver.SemVer

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / version := SbtProjectInfo.ProjectVersion
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
ThisBuild / testFrameworks ~= (testFws => (TestFramework("hedgehog.sbt.Framework") +: testFws).distinct)

lazy val hedgehogExtra = Project("hedgehog-extra", file("."))
  .settings(
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .aggregate(extraCore, extraRefined)

lazy val extraCore = subProject(ProjectName("core"))
  .settings(
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )

lazy val extraRefined = subProject(ProjectName("refined"))
  .settings(
    libraryDependencies ++= (SemVer.parseUnsafe(scalaVersion.value) match {
      case SemVer(SemVer.Major(2), SemVer.Minor(11), _, _, _) =>
        Seq("eu.timepit" %% "refined" % "0.9.12" excludeAll("org.scala-lang.modules" %% "scala-xml"))
      case _                                                  =>
        Seq("eu.timepit" %% "refined" % "0.9.27" excludeAll("org.scala-lang.modules" %% "scala-xml"))
    }),
    libraryDependencies := removeDottyIncompatible(isScala3(scalaVersion.value), libraryDependencies.value)
  )
  .dependsOn(extraCore)

lazy val props =
  new {
    final val Org            = "io.kevinlee"
    final val GitHubUsername = "Kevin-Lee"
    final val RepoName       = "scala-hedgehog-extra"

    final val ProjectScalaVersion = "2.13.6"
//    final val ProjectScalaVersion = "3.0.0"
    final val CrossScalaVersions  =
      Seq(
        "2.11.12",
        "2.12.13",
        "2.13.6",
        ProjectScalaVersion
      ).distinct

    final val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    final val hedgehogVersion = "0.8.0"

    final val IncludeTest = "compile->compile;test->test"
  }

lazy val libs =
  new {
    lazy val hedgehogLibs = Seq(
      "qa.hedgehog" %% "hedgehog-core"   % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.hedgehogVersion
    )

    lazy val hedgehogLibsForTesting =
      (hedgehogLibs ++ Seq(
        "qa.hedgehog" %% "hedgehog-sbt" % props.hedgehogVersion
      )).map(_ % Test)
  }

def removeDottyIncompatible(isScala3: Boolean, libraries: Seq[ModuleID]): Seq[ModuleID] =
  if (isScala3)
    libraries.filterNot(props.removeDottyIncompatible).distinct
  else
    libraries.distinct

//lazy val extra =

// format: off
def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// format: on

def subProject(projectName: ProjectName): Project = {
  val prefixedName = prefixedProjectName(projectName.projectName)
  Project(prefixedName, file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      crossScalaVersions := props.CrossScalaVersions,
      testFrameworks ~= (testFws => (TestFramework("hedgehog.sbt.Framework") +: testFws).distinct),
      libraryDependencies ++= libs.hedgehogLibs ++ libs.hedgehogLibsForTesting,
      /* WartRemover and scalacOptions { */
      Compile / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value),
      Test / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value),
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
      /* Ammonite-REPL { */
      libraryDependencies ++=
        (scalaBinaryVersion.value match {
          case "2.10" =>
            Seq.empty[ModuleID]
          case "2.11" =>
            Seq("com.lihaoyi" % "ammonite" % "1.6.7" % Test cross CrossVersion.full)
          case "2.12" | "2.13" | "3" =>
            Seq("com.lihaoyi" % "ammonite" % "2.5.3" % Test cross CrossVersion.full)
          case _ =>
            Seq.empty[ModuleID]
        }),
      Test / sourceGenerators +=
        (scalaBinaryVersion.value match {
          case "2.10" | "2.11" =>
            task(Seq.empty[File])
          case "2.12" | "2.13" | "3" =>
            task {
              val file = (Test / sourceManaged).value / "amm.scala"
              IO.write(file, """object amm extends App { ammonite.AmmoniteMain.main(args) }""")
              Seq(file)
            }
          case _ =>
            task(Seq.empty[File])
        })
      /* } Ammonite-REPL */

    )
}

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.0")
