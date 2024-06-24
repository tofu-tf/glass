import sbt._
import Keys._
import org.apache.ivy.core.module.descriptor.ExcludeRule

object Dependencies {
  val minorVersion = SettingKey[Int]("minor scala version")

  object Version {
    val scala212 = "2.12.19"

    val scala213 = "2.13.14"

    val scala3 = "3.3.3"

    val cats = "2.12.0"

    val monocle = "3.2.0"

    val monocle212 = "2.1.0"

    val scalatest = "3.2.19"

    // Compile time only
    val macroParadise = "2.1.1"

    val kindProjector = "0.13.3"

    val betterMonadicFor = "0.3.1"

    val collectionCompat = "2.12.0"
  }

  val noCatsCore       =
    Vector(ExclusionRule("org.typelevel", "cats-core_2.13"), ExclusionRule("org.typelevel", "cats-core_2.12"))
  val catsCore         = "org.typelevel"              %% "cats-core"               % Version.cats
  val monocle          = "dev.optics"                 %% "monocle-core"            % Version.monocle excludeAll (noCatsCore: _*)
  val monocle212       = "com.github.julien-truffaut" %% "monocle-core"            % Version.monocle212 excludeAll (noCatsCore: _*)
  val alleycats        = "org.typelevel"              %% "alleycats-core"          % Version.cats
  val scalatest        = "org.scalatest"              %% "scalatest"               % Version.scalatest % Test
  val collectionCompat = "org.scala-lang.modules"     %% "scala-collection-compat" % Version.collectionCompat
  // Compile-time only
  val macroParadise    = "org.scalamacros"             % "paradise"                % Version.macroParadise cross CrossVersion.patch
  val kindProjector    = "org.typelevel"              %% "kind-projector"          % Version.kindProjector cross CrossVersion.patch
  val betterMonadicFor = "com.olegpy"                 %% "better-monadic-for"      % Version.betterMonadicFor
}
