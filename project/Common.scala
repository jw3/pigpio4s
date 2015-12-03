import sbt.Keys._
import sbt._

object Common {
    val settings: Seq[Def.Setting[_]] = Seq(
        organization := "wiii",
        licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),

        scalaVersion := "2.11.7",
        scalacOptions += "-target:jvm-1.8"
    )
}