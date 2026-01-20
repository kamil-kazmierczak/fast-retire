plugins {
    id("org.springframework.boot") version "4.0.1"
}

dependencies {
    implementation(project(":fast-retire-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")


    runtimeOnly("org.postgresql:postgresql")
}