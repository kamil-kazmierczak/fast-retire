dependencies {
    implementation(project(":fast-retire-integration:fast-retire-integration-api"))
    implementation(project(":fast-retire-api"))
    implementation(project(":fast-retire-application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
}