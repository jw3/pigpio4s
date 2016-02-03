import sbt._

object Dependencies {
    val commonDependencies = {
        val akkaVersion = "2.4.1"
        val akkaStreamVersion = "2.0.1"

        Seq(
            "org.scala-lang" % "scala-library" % "2.11.7",
            "org.scala-lang" % "scala-reflect" % "2.11.7",

            "wiii" %% "akka-injects" % "0.1",
            "gpio4s" %% "gpiocfg" % "0.1",
            "io.reactivex" %% "rxscala" % "0.25.0",

            "net.java.dev.jna" % "jna" % "4.2.1",
            "com.nativelibs4java" % "jnaerator-runtime" % "0.12",

            "com.typesafe.akka" %% "akka-actor" % akkaVersion,
            "com.typesafe.akka" %% "akka-slf4j" % akkaVersion % Runtime,
            "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,

            "org.scalatest" %% "scalatest" % "2.2.5" % Test,
            "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % Test,
            "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
        )
    }
}

