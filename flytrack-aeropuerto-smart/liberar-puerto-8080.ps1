# Cierra el proceso que está escuchando en el puerto 8080 (útil si quedó colgada una instancia de FlyTrack).
# ATENCIÓN: si otro programa usa 8080, también lo cerrará.
$ErrorActionPreference = "Stop"
$port = 8080
$conns = @(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue)
if ($conns.Count -eq 0) {
    Write-Host "Nadie está escuchando en el puerto $port."
    exit 0
}
$pids = $conns | Select-Object -ExpandProperty OwningProcess -Unique
foreach ($procId in $pids) {
    $p = Get-Process -Id $procId -ErrorAction SilentlyContinue
    $name = if ($p) { $p.ProcessName } else { "?" }
    Write-Host "Deteniendo PID $procId ($name) que usa el puerto $port..."
    Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
}
Start-Sleep -Seconds 1
Write-Host "Listo. Vuelve a ejecutar .\arrancar.ps1"
