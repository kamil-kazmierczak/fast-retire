dependencies {
    implementation(project(":fast-retire-api"))
    implementation(project(":fast-retire-application"))
    implementation(project(":fast-retire-integration:fast-retire-integration-api"))
    implementation(project(":fast-retire-integration:fast-retire-integration-application"))
    implementation(project(":fast-retire-integration:fast-retire-integration-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jackson")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}