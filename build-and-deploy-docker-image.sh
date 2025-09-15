#!/bin/bash
# build.sh - Automatyczny build z wersją z pom.xml

# Pobierz wersję z pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
APP_NAME="kamilkazmierczak/fast-retire-standalone"

echo "🏗️ Building JAR..."
mvn clean package -DskipTests

echo "🐳 Building Docker image version $VERSION..."
docker build \
  -t $APP_NAME:$VERSION \
  -t $APP_NAME:latest \
  .

echo "📤 Pushing to Docker Hub..."
docker push $APP_NAME:$VERSION
docker push $APP_NAME:latest

echo "✅ Done! Published:"
echo "  - $APP_NAME:$VERSION"
echo "  - $APP_NAME:latest"
