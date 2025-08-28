plugins {
	kotlin("jvm") version "2.0.21"
	kotlin("plugin.spring") version "2.0.21"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
//	id("org.liquibase.gradle") version "2.2.0"
}

group = "com.carbon.relay.integration"
version = "0.5.0"
description = "Carbon Relay Integration"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
//	mavenLocal()
	mavenCentral()
}

dependencies {

	val springBootVersion = "3.5.4"
	val kotlinxCoroutinesVersion = "1.10.1"
	val arrow = "1.2.4"
	val springDoc = "2.8.5"
	val resilience4j = "2.2.0"
	val kotest = "5.9.1"


	// Spring
	implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
//	implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-amqp:$springBootVersion")
	runtimeOnly("org.springdoc:springdoc-openapi-starter-webflux-ui:$springDoc")
	implementation("org.springdoc:springdoc-openapi-starter-common:$springDoc")
	implementation("org.springframework.kafka:spring-kafka:3.2.4")
//	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")



	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


	//postgresql
	implementation("org.postgresql:r2dbc-postgresql")
	implementation("com.zaxxer:HikariCP:7.0.0")
	implementation("org.postgresql:postgresql:42.7.7")  // Move from runtimeOnly to implementation

	// JobRuner
	implementation("org.jobrunr:jobrunr:7.5.2")


	// Kotlinx
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
	runtimeOnly("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")

	// Kotlin logger
	implementation("io.github.oshai:kotlin-logging-jvm:7.0.5")


	// Liquibase
	implementation("org.liquibase:liquibase-core:4.23.2")
	//liquibaseRuntime("org.liquibase:liquibase-core:4.23.2")
	//liquibaseRuntime("org.postgresql:postgresql:42.7.7")
	//liquibaseRuntime("info.picocli:picocli:4.7.4")
	//runtimeOnly("org.postgresql:postgresql")

	//bucket4j
	implementation("com.bucket4j:bucket4j_jdk17-core:8.15.0")
	implementation("com.bucket4j:bucket4j_jdk17-redis-common:8.15.0")
	implementation("com.bucket4j:bucket4j_jdk17-lettuce:8.15.0")

}


kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
