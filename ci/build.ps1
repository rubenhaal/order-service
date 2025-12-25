Write-Host "==> Building order-service (clean build)..." 

# Salir si algún comando falla
$ErrorActionPreference = "Stop"

# Ejecutar gradle wrapper
./gradlew.bat clean build

Write-Host "==> Build completed successfully."
