plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fast-retire-integration:fast-retire-integration-api"))
    implementation(project(":fast-retire-application"))

    implementation("org.springframework.boot:spring-boot-starter-webmvc:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jackson:4.0.1")
}
