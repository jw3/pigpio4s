import sbt._

object Dependencies {
    val commonDependencies: Seq[ModuleID] = Seq(
        "io.reactivex" %% "rxscala" % "0.25.0",

        "net.java.dev.jna" % "jna" % "4.2.1",
        "com.nativelibs4java" % "jnaerator-runtime" % "0.12",

        "org.scalatest" %% "scalatest" % "2.2.5" % Test,
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
}

