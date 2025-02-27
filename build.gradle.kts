import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	application
	checkstyle
	jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.10"
	id("io.sentry.jvm.gradle") version "5.2.0"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

application {
	mainClass = "hexlet.code.AppApplication"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

checkstyle {
	configFile = file("config/checkstyle/checkstyle.xml")
	toolVersion = "10.13.0"
}

repositories {
	mavenCentral()
}

//dependencies {
//	implementation("org.springframework.boot:spring-boot-starter")
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.boot:spring-boot-devtools")
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-validation")
//	testImplementation("org.springframework.security:spring-security-test")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//
//	implementation("org.mapstruct:mapstruct:1.5.5.Final")
//	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
//	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
//
//
//
//	// assertj // jupiter
//	testImplementation("org.assertj:assertj-core:3.27.2")
//	testImplementation(platform("org.junit:junit-bom:5.10.0"))
//	testImplementation("org.junit.jupiter:junit-jupiter")
//	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
//
//
//	// postgresql
//	// https://mvnrepository.com/artifact/org.postgresql/postgresql
//	implementation("org.postgresql:postgresql:42.7.5")
//
//	runtimeOnly("com.h2database:h2:2.3.232")
//
//	implementation("net.datafaker:datafaker:2.4.2")
//	testImplementation ("org.instancio:instancio-junit:5.3.0")
//
//	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
//
//}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// implementation("io.github.wimdeblauwe:error-handling-spring-boot-starter:4.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	implementation("org.instancio:instancio-junit:3.3.0")
	implementation("org.apache.commons:commons-lang3:3.13.0")
	implementation("org.apache.commons:commons-text:1.10.0")
	implementation("net.datafaker:datafaker:2.0.1")

	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")

	runtimeOnly("com.h2database:h2")
	// runtimeOnly("org.postgresql:postgresql:42.6.0")
}

//tasks.withType<Checkstyle> {
//	enabled = false
//}

tasks.withType<Test> {
//	enabled = false
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

sentry {
	includeSourceContext = true

	org = "nikita-ryazanov"
	projectName = "java-spring-boot"
	authToken = System.getenv("SENTRY_AUTH_TOKEN")
}

tasks.sentryBundleSourcesJava {
	enabled = System.getenv("SENTRY_AUTH_TOKEN") != null
}

//tasks.register<JavaExec>("debug") {
//	group = "application"
//	description = "Runs the application in debug mode"
//
//	mainClass.set("hexlet.code.AppApplication") // Укажите свой основной класс с методом main()
//	classpath = sourceSets["main"].runtimeClasspath
//
//	jvmArgs = listOf(
//		"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
//	)
//}
