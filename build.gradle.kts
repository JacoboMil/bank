import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.3.0"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
}

group = "com.iobuilders"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

ext["log4j2.version"] = "${property("log4jVersion")}"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
    dependsOn("generateServer")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-core:${property("logbackVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

    implementation("org.springdoc:springdoc-openapi-data-rest:${property("openapiVersion")}")
    implementation("org.springdoc:springdoc-openapi-ui:${property("openapiVersion")}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${property("openapiVersion")}")
    implementation("javax.validation:validation-api:2.0.1.Final")

    implementation("org.zalando:problem-spring-web:${property("problemVersion")}")
    implementation("org.zalando:problem:${property("problemVersion")}")
    implementation("org.zalando:jackson-datatype-problem:${property("problemVersion")}")
    implementation("org.zalando:problem-gson:${property("problemVersion")}")

    implementation("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${property("mockitoVersion")}")
    testImplementation("com.h2database:h2")

}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

val oasPackage = "com.iobuilders.bank"
val oasSpecLocation = "src/main/resources/JacoboMil-io-builders-2.0.0-oas3-resolved.yaml"
val oasGenOutputDir = project.layout.buildDirectory.dir("generated-oas")

tasks.register("generateServer", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    input = project.file(oasSpecLocation).path
    outputDir.set(oasGenOutputDir.get().toString())
    modelPackage.set("$oasPackage.model")
    apiPackage.set("$oasPackage.api")
    packageName.set(oasPackage)
    generatorName.set("kotlin-spring")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "useTags" to "true"
        )
    )
}

sourceSets {
    val main by getting
    main.java.srcDir("${oasGenOutputDir.get()}/src/main/kotlin")
}



