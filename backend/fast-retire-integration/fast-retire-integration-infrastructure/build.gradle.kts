dependencies {
    implementation(project(":fast-retire-integration:fast-retire-integration-api"))
    implementation(project(":fast-retire-application"))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jackson")
}
