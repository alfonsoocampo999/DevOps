# Arranca FlyTrack sin tener Maven en el PATH (descarga Maven portable la primera vez).
# Si el puerto 8080 está ocupado por una instancia anterior de este proyecto, intenta liberarlo.
$ErrorActionPreference = "Stop"
$root = $PSScriptRoot

function Stop-FlyTrackListeningOn8080 {
    $port = 8080
    $conns = @(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue)
    foreach ($c in $conns) {
        $procId = $c.OwningProcess
        try {
            $w = Get-CimInstance Win32_Process -Filter "ProcessId=$procId" -ErrorAction SilentlyContinue
            $cmd = if ($w) { $w.CommandLine } else { "" }
            if ($cmd -match "(?i)flytrack|FlyTrackApplication") {
                Write-Host "Puerto $port ocupado por una instancia anterior de FlyTrack (PID $procId). Cerrando..."
                Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
            }
        } catch {}
    }
    Start-Sleep -Seconds 1
    $still = @(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue)
    if ($still.Count -gt 0) {
        Write-Warning "El puerto $port sigue en uso. Ejecuta antes: .\liberar-puerto-8080.ps1"
        Write-Warning "O cierra manualmente el programa que usa ese puerto."
    }
}

Stop-FlyTrackListeningOn8080
$pom = Join-Path $root "pom.xml"
$mavenDir = Join-Path $root ".maven"
$mvnHome = Join-Path $mavenDir "apache-maven-3.9.9"
$mvnCmd = Join-Path $mvnHome "bin\mvn.cmd"

if (-not (Test-Path $pom)) {
    Write-Error "No se encontro pom.xml en $root"
    exit 1
}

if (-not (Test-Path $mvnCmd)) {
    Write-Host "Primera ejecucion: descargando Apache Maven 3.9.9 (~9 MB)..."
    New-Item -ItemType Directory -Force -Path $mavenDir | Out-Null
    $zip = Join-Path $mavenDir "apache-maven-bin.zip"
    $urls = @(
        "https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip",
        "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip"
    )
    $ok = $false
    foreach ($u in $urls) {
        try {
            Invoke-WebRequest -Uri $u -OutFile $zip -UseBasicParsing
            $ok = $true
            break
        } catch {
            Write-Warning "Fallo la URL: $u"
        }
    }
    if (-not $ok) {
        Write-Error "No se pudo descargar Maven. Comprueba tu conexion o instala Maven manualmente."
        exit 1
    }
    Expand-Archive -Path $zip -DestinationPath $mavenDir -Force
    Remove-Item $zip -Force -ErrorAction SilentlyContinue
    if (-not (Test-Path $mvnCmd)) {
        Write-Error "Extraccion incompleta: no existe $mvnCmd"
        exit 1
    }
    Write-Host "Maven listo en .maven\"
}

Write-Host "Iniciando Spring Boot (PostgreSQL debe estar en marcha)..."
Push-Location $root
try {
    & $mvnCmd -f $pom spring-boot:run @args
} finally {
    Pop-Location
}
