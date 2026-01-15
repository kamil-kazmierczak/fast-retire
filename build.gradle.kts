plugins {
    id("java")
    id("org.springframework.boot") version "4.0.1" apply false
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-restclient:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jackson:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-webmvc:4.0.1")
    implementation("org.springframework.boot:spring-boot-configuration-processor:4.0.1")


    implementation("io.micrometer:micrometer-tracing:1.6.0")
    implementation("io.micrometer:micrometer-tracing-bridge-otel:1.6.0")
    implementation("org.postgresql:postgresql:42.7.8")


    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")


    testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.1")
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.1"))

        compileOnly("org.projectlombok:lombok:1.18.42")
        annotationProcessor("org.projectlombok:lombok:1.18.42")


        testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.1")
    }

}