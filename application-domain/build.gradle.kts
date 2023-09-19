plugins {
	kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

dependencies {

	// spring transaction
	implementation(Dependencies.SPRING_TRANSACTION)

	// bytebuddy
	implementation(Dependencies.BYTEBUDDY)
}

allOpen {
	annotation("team.comit.simtong.global.annotation.UseCase")
	annotation("team.comit.simtong.global.annotation.ReadOnlyUseCase")
}