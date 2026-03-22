# === Stage 1: Build frontend ===
FROM node:22-alpine AS frontend-build
WORKDIR /app
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build -- --configuration production

# === Stage 2: Build backend ===
FROM eclipse-temurin:25-jdk-alpine AS backend-build
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY backend/ backend/
COPY --from=frontend-build \
  /app/dist/fast-retire-frontend/browser/ \
  backend/fast-retire-standalone/src/main/resources/static/
RUN chmod +x gradlew && ./gradlew -p backend :fast-retire-standalone:bootJar --no-daemon

# === Stage 3: Runtime ===
FROM eclipse-temurin:25-jre-alpine
COPY --from=backend-build \
  /app/backend/fast-retire-standalone/build/libs/*-standalone.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
