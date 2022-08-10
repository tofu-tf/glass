import Publish._, Dependencies._
import com.typesafe.sbt.SbtGit.git
import sbt.ModuleID

lazy val setMinorVersion = minorVersion := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, v)) => v.toInt
    case _            => 0
  }
}

lazy val defaultSettings = Seq(
  scalaVersion       := Version.scala213,
  crossScalaVersions := Vector(Version.scala212, Version.scala213, Version.scala3),
  setMinorVersion,
  defaultScalacOptions,
  scalacWarningConfig,
  Compile / doc / scalacOptions -= "-Xfatal-warnings",
  libraryDependencies ++= Seq(
    scalatest,
    collectionCompat
  ),
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        Seq(
          compilerPlugin(kindProjector),
          compilerPlugin(betterMonadicFor),
          scalaOrganization.value % "scala-reflect" % scalaVersion.value % Provided
        )
      case _            => Seq.empty
    }
  },
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) => Seq()
      case Some((2, _)) => Seq("-Xsource:3")
      case _            => Seq.empty
    }
  }
) ++ macros

lazy val opticsCore = project
  .in(file("modules/core"))
  .settings(
    defaultSettings,
    libraryDependencies ++= Seq(catsCore, alleycats),
    name := "glass-core"
  )

lazy val opticsInterop = project
  .in(file("modules/interop"))
  .dependsOn(opticsCore)
  .settings(
    name := "glass-interop",
    defaultSettings,
    libraryDependencies += catsCore,
    libraryDependencies += {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 12)) => monocle212
        case _             => monocle
      }
    }
  )

lazy val opticsMacro = project
  .in(file("modules/macro"))
  .dependsOn(opticsCore)
  .settings(
    defaultSettings,
    scalacOptions ~= { opts =>
      val suppressed = List(
        "unused:params",
        "unused:patvars"
      )
      opts.filterNot(opt => suppressed.exists(opt.contains))
    },
    name := "glass-macro"
  )

lazy val coreModules =
  Vector(
    opticsMacro,
    opticsCore
  )

lazy val commonModules =
  Vector(opticsInterop)

lazy val allModuleRefs  = (coreModules ++ commonModules).map(x => x: ProjectReference)
lazy val mainModuleDeps = (coreModules ++ commonModules).map(x => x: ClasspathDep[ProjectReference])

lazy val glass = project
  .in(file("."))
  .settings(
    defaultSettings,
    name := "glass"
  )
  .aggregate(
    (coreModules ++ commonModules).map(x => x: ProjectReference): _*
  )
  .dependsOn(coreModules.map(x => x: ClasspathDep[ProjectReference]): _*)

lazy val defaultScalacOptions = scalacOptions := {
  val tpolecatOptions = scalacOptions.value

  val dropLints = Set(
    "-Ywarn-dead-code",
    "-Wdead-code" // ignore dead code paths where `Nothing` is involved
  )

  val opts = tpolecatOptions.filterNot(dropLints)

  // drop `-Xfatal-warnings` on dev and 2.12 CI
  if (!sys.env.get("CI").contains("true") || (minorVersion.value == 12))
    opts.filterNot(Set("-Xfatal-warnings"))
  else
    opts
}

lazy val scalacWarningConfig = scalacOptions += {
  // // ignore unused imports that cannot be removed due to cross-compilation
  // val suppressUnusedImports = Seq(
  //   "scala/tofu/config/typesafe.scala"
  // ).map { src =>
  //   s"src=${scala.util.matching.Regex.quote(src)}&cat=unused-imports:s"
  // }.mkString(",")

  // print warning category for fine-grained suppressing, e.g. @nowarn("cat=unused-params")
  val contextDeprecationInfo = "cat=deprecation&msg=^(.*((Has)|(With)|(Logging)).*)$:silent"
  val verboseWarnings        = "any:wv"

  s"-Wconf:$contextDeprecationInfo,$verboseWarnings"
}

lazy val macros = Seq(
  scalacOptions ++= { if (minorVersion.value == 13) Seq("-Ymacro-annotations") else Seq() },
  libraryDependencies ++= { if (minorVersion.value == 12) Seq(compilerPlugin(macroParadise)) else Seq() }
)

lazy val noPublishSettings =
  defaultSettings ++ Seq(publish := {}, publishArtifact := false, publishTo := None, publish / skip := true)

addCommandAlias("fmt", "all glass/scalafmtSbt glass/scalafmtAll")
addCommandAlias("checkfmt", "all glass/scalafmtSbtCheck glass/scalafmtCheckAll")

addCommandAlias("preparePR", "scalafmtAll ;scalafmtSbt ;reload; githubWorkflowGenerate; clean; Test / compile")
