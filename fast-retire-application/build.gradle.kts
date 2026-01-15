plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fast-retire-api"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")
}