ThisBuild / scalaVersion := Dependencies.Version.scala213

ThisBuild / crossScalaVersions                  := Vector(
  Dependencies.Version.scala3,
  Dependencies.Version.scala213,
  Dependencies.Version.scala212
)

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")

ThisBuild / githubWorkflowPublishTargetBranches := Seq(
  RefPredicate.Equals(Ref.Branch("main")),
  RefPredicate.StartsWith(Ref.Tag("v"))
)

ThisBuild / githubWorkflowJavaVersions          := Seq(JavaSpec.temurin("8"), JavaSpec.temurin("17"))

ThisBuild / githubWorkflowBuildPreamble += WorkflowStep.Sbt(
  List("scalafmtCheckAll", "scalafmtSbtCheck"),
  name = Some("Check formatting")
)

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    name = Some("Publish artifacts"),
    env = Map(
      "PGP_PASSPHRASE"    -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET"        -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)
ThisBuild / versionScheme         := Some("semver-spec")

ThisBuild / licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

ThisBuild / developers       := List(
  Developer("catostrophe", "λoλcat", "catostrophe@pm.me", url("https://github.com/catostrophe")),
  Developer("danslapman", "Daniil Smirnov", "danslapman@gmail.com", url("https://github.com/danslapman")),
  Developer("odomontois", "Oleg Nizhnik", "odomontois@gmail.com", url("https://github.com/odomontois")),
  Developer("oskin1", "Ilya Oskin", "ilya.arcadich@gmail.com", url("https://github.com/oskin1")),
)

ThisBuild / organization     := "tf.tofu"
ThisBuild / organizationName := "Tofu"

ThisBuild / homepage := Some(url("https://github.com/tofu-tf/glass"))

ThisBuild / description := "Modern optic library for Scala"

ThisBuild / scmInfo                   := Some(
  ScmInfo(
    url("https://github.com/tofu-tf/glass"),
    "git@github.com:tofu-tf/glass.git"
  )
)

ThisBuild / githubWorkflowEnv += "CI" -> "true"
