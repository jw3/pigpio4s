import sbt._

object Dependencies {
    val commonDependencies: Seq[ModuleID] = Seq(
        "org.scala-lang" % "scala-library" % "2.11.7",
        "org.scala-lang" % "scala-reflect" % "2.11.7",

        "gpio4s" %% "gpiocfg" % "0.1",
        "io.reactivex" %% "rxscala" % "0.25.0",

        "net.java.dev.jna" % "jna" % "4.2.1",
        "com.nativelibs4java" % "jnaerator-runtime" % "0.12",

        "com.typesafe.akka" %% "akka-actor" % "2.4.0",
        "com.typesafe.akka" %% "akka-slf4j" % "2.4.0" % Runtime,

        "org.scalatest" %% "scalatest" % "2.2.5" % Test,
        "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % Test,
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
    )
}

