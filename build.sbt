enablePlugins(GitVersioning)

import sbt.Keys._
import sbt._

git.baseVersion := "0.0.1"
git.useGitDescribe := true

lazy val pigpio = (project in file("pigpio"))
                  .settings(Common.settings: _*)
                  .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val core = (project in file("core"))
                .dependsOn(pigpio)
                .settings(Common.settings: _*)
                .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val akka = (project in file("akka"))
                .dependsOn(pigpio, core)
                .settings(Common.settings: _*)
                .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val devices = (project in file("devices"))
                .dependsOn(pigpio, core, akka)
                .settings(Common.settings: _*)
                .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val examples = (project in file("examples"))
                    .dependsOn(pigpio, core, akka, devices)
                    .settings(Common.settings: _*)
                    .settings(libraryDependencies ++= Dependencies.commonDependencies)
