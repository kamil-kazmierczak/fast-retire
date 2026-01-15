FROM eclipse-temurin:25-jre-alpine
COPY fast-retire-standalone/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
