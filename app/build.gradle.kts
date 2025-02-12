import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	application
	checkstyle
	jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

application {
	mainClass.set("hexlet.code.AppApplication")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	// assertj // jupiter
	testImplementation("org.assertj:assertj-core:3.27.2")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
	// https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
		events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
		// showStackTraces = true
		// showCauses = true
		showStandardStreams = true
	}
}

tasks.jacocoTestReport {
	reports {
		xml.required = true
		csv.required = false
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}