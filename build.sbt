enablePlugins(GitVersioning)

import sbt.Keys._
import sbt._

git.baseVersion := "0.1"
git.useGitDescribe := true

lazy val core = (project in file("core"))
                .settings(Common.settings: _*)
                .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val examples = (project in file("examples"))
                    .dependsOn(core)
                    .settings(Common.settings: _*)
                    .settings(libraryDependencies ++= Dependencies.commonDependencies)
