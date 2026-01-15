plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fast-retire-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
}