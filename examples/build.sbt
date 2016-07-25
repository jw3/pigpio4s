name := RxGpioBuild.NamePrefix + "examples"

test in assembly := {}

assembleArtifact in assemblyPackageScala := true

enablePlugins(JavaAppPackaging)
mainClass in Compile := Some("rxgpio.examples.ButtonPushed")
dockerRepository := Some("192.168.0.11:5000/jwiii")
dockerBaseImage := "jwiii/arm-java:8"
