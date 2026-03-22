#!/usr/bin/env bash
set -euo pipefail

APP_NAME="kamilkazmierczak/fast-retire-frontend"
VERSION=$(node -p "require('./package.json').version")

echo "🧹 npm ci"
npm ci

echo "🛠️ ng build (production)"
npx ng build --configuration production

echo "🐳 docker build $APP_NAME:$VERSION"
docker build \
  -t "$APP_NAME:$VERSION" \
  -t "$APP_NAME:latest" \
  .

echo "📤 docker push"
docker push "$APP_NAME:$VERSION"
docker push "$APP_NAME:latest"

echo "✅ Published:"
echo "  - $APP_NAME:$VERSION"
echo "  - $APP_NAME:latest"
