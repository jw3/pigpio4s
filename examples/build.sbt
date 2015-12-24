name := RxGpioBuild.NamePrefix + "examples"

mainClass in assembly := Option("rxgpio.examples.Driver")

test in assembly := {}

assembleArtifact in assemblyPackageScala := true