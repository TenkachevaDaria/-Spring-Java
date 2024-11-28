plugins {
    java
    id("org.springframework.boot") version "3.4.0"
}

apply(plugin = "io.spring.dependency-management")

tasks.named<JavaExec>("bootRun") {
    standardInput = System.`in`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.shell:spring-shell-starter:3.2.5")
}