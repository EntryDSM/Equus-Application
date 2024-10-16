plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    application
}

dependencyManagement {
    imports {
        mavenBom(Dependencies.SPRING_CLOUD)
    }
}

application {
    mainClass.set("hs.kr.equus.application.EquusApplicationKt")
}

dependencies {
    // impl project
    implementation(project(":application-domain"))

    // web
    implementation(Dependencies.SPRING_WEB)

    // validation
    implementation(Dependencies.SPRING_VALIDATION)

    // kotlin
    implementation(Dependencies.JACKSON)

    // security
    implementation(Dependencies.SPRING_SECURITY)

    //jwt
    implementation(Dependencies.JWT)

    // database
    implementation(Dependencies.SPRING_DATA_JPA)
    runtimeOnly(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.REDIS)
    implementation(Dependencies.SPRING_REDIS)

    // querydsl
    implementation(Dependencies.QUERYDSL)
    kapt(Dependencies.QUERYDSL_PROCESSOR)

    // aws
    implementation(Dependencies.SPRING_AWS)

    // mapstruct
    implementation(Dependencies.MAPSTRUCT)
    kapt(Dependencies.MAPSTRUCT_PROCESSOR)

    // read-file
    implementation(Dependencies.COMMONS_IO)
    implementation(Dependencies.POI)
    implementation(Dependencies.POI_OOXML)

    // sentry
    implementation(Dependencies.SENTRY)

    // configuration
    annotationProcessor(Dependencies.CONFIGURATION_PROCESSOR)

    // s3mock
    testImplementation(Dependencies.S3MOCK)

    // Feign Client
    implementation(Dependencies.OPEN_FEIGN)

    // Cloud Config
    implementation(Dependencies.CLOUD_CONFIG)

    // Kafka
    implementation(Dependencies.KAFKA)

    // Actuator
    implementation(Dependencies.ACTUATOR)
    //resilience4j
    implementation(Dependencies.RESILIENCE4J)

    implementation(Dependencies.CACHE)

//    implementation(Dependencies.PDF_ITEXT)
    implementation(Dependencies.PDF_HTML)
    implementation (Dependencies.THYMELEAF)

    implementation(Dependencies.RETRY)

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.unmappedTargetPolicy", "ignore")
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}