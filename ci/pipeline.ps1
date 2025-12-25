Write-Host "==> Starting local pipeline (build + test)..."

$ErrorActionPreference = "Stop"

.\ci\build.ps1
.\ci\test.ps1

Write-Host "==> Local pipeline finished successfully."
