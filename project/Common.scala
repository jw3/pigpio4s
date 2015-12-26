import sbt.Keys._
import sbt._

object Common {
    val settings: Seq[Def.Setting[_]] = Seq(
        organization := "wiii",
        licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
        resolvers += "jw3 at bintray" at "https://dl.bintray.com/jw3/maven",
        scalaVersion := "2.11.7",
        scalacOptions += "-target:jvm-1.8"
    )
}