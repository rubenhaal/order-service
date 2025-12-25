Write-Host "==> Starting order-service with profile 'prod'..."

$ErrorActionPreference = "Stop"

$env:SPRING_PROFILES_ACTIVE = "prod"

if (-not (Test-Path "build\libs")) {
    Write-Host "No build found in build/libs, running clean build first..."
    ./gradlew.bat clean build
}

$jar = Get-ChildItem -Path "build\libs" -Filter "order-service-*.jar" | Select-Object -First 1

if (-not $jar) {
    Write-Error "No JAR found in build/libs matching 'order-service-*.jar'"
    exit 1
}

Write-Host "Running JAR:" $jar.FullName

java -jar $jar.FullName
