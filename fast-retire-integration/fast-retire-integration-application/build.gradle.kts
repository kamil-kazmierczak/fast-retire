plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fast-retire-integration:fast-retire-integration-api"))
    implementation(project(":fast-retire-api"))
    implementation(project(":fast-retire-application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-webmvc:4.0.1")
}