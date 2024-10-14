plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

repositories {
    mavenCentral()
}

dependencies {
    // spring transaction
    implementation(Dependencies.SPRING_TRANSACTION)

    // bytebuddy
    implementation(Dependencies.BYTEBUDDY)
}

allOpen {
    annotation("hs.kr.equus.application.global.annotation.UseCase")
    annotation("hs.kr.equus.application.global.annotation.ReadOnlyUseCase")
}
