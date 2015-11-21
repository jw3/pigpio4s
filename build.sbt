name := "pigpio4s"
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies ++= {
    Seq(
        "net.java.dev.jna" % "jna" % "4.2.1",
        "com.nativelibs4java" % "jnaerator-runtime" % "0.12"
    )
}