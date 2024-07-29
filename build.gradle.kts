plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("kapt") version "1.9.24"
}

group = "com.casestudy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.mysql:mysql-connector-j")
	implementation("org.projectlombok:lombok")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.apache.commons:commons-lang3:3.14.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	// Annotation Processor dependencies
	kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.testcontainers:testcontainers") // Core Testcontainers dependency
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")

	// JUnit 5
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

	// Mockito
	testImplementation("org.mockito:mockito-core:5.3.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.3.0")

	// For Kotlin projects
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")



}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}