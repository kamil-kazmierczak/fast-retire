FROM eclipse-temurin:21-jre-alpine
COPY fast-retire-standalone/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
