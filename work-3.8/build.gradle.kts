plugins {
    java
    id("org.springframework.boot") version "3.4.0"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.3.232")
    implementation("mysql:mysql-connector-java:8.0.33")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
