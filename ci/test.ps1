Write-Host "==> Running tests for order-service..."

$ErrorActionPreference = "Stop"

./gradlew.bat test

Write-Host "==> Tests finished successfully."
