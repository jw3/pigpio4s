organization := "wiii"

name := "pigpio4s"

version := "0.1-SNAPSHOT"

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.11.7"

scalacOptions += "-target:jvm-1.8"

resolvers += "jw3 at bintray" at "https://dl.bintray.com/jw3/maven"

libraryDependencies ++= {
    Seq(
        "net.java.dev.jna" % "jna" % "4.2.1",
        "com.nativelibs4java" % "jnaerator-runtime" % "0.12",

        "org.scalatest" %% "scalatest" % "2.2.5" % Test,
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
}
