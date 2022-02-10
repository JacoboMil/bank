import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.3.0"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
}

group = "com.iobuilders"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.5")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.5")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.5")
    implementation("javax.validation:validation-api:2.0.1.Final")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.h2database:h2")
}

dependencyManagement {
    imports {
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

