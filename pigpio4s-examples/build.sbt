name := PiGpio4sBuild.NamePrefix + "examples"

mainClass in assembly := Option("pi4s.examples.Driver")

test in assembly := {}

assembleArtifact in assemblyPackageScala := true