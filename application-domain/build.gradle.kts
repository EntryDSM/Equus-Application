plugins {
	kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

dependencies {

	// spring transaction
	implementation(Dependencies.SPRING_TRANSACTION)

	// bytebuddy
	implementation(Dependencies.BYTEBUDDY)
}