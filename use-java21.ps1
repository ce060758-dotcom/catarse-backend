# use-java21.ps1
Write-Host "=== REMOVENDO JAVA 25 E CONFIGURANDO JAVA 21 ===" -ForegroundColor Cyan

# 1. Mostrar Java atual
Write-Host "Java atual no PATH:" -ForegroundColor Yellow
where.exe java

# 2. Configurar Java 21
$java21Path = "C:\Users\Public\Downloads\jdk-21.0.10"

if (Test-Path "$java21Path\bin\java.exe") {
    # Adicionar Java 21 no INÍCIO do PATH
    $env:PATH = "$java21Path\bin;$env:PATH"
    $env:JAVA_HOME = $java21Path
    
    Write-Host "`n✅ Java 21 configurado: $java21Path" -ForegroundColor Green
} else {
    Write-Host "❌ Java 21 não encontrado em: $java21Path" -ForegroundColor Red
    Write-Host "📥 Baixe Java 21 LTS: https://adoptium.net/temurin/releases/?version=21" -ForegroundColor Yellow
    exit 1
}

# 3. Verificar
Write-Host "`n=== VERIFICAÇÃO ===" -ForegroundColor Cyan
Write-Host "Java version:" -ForegroundColor Yellow
java -version

Write-Host "`nJava locations:" -ForegroundColor Yellow
where.exe java

Write-Host "`n✅ Pronto para usar Java 21!" -ForegroundColor Green
Write-Host "`nPara compilar seu projeto:" -ForegroundColor Cyan
Write-Host "cd C:\Users\55119\catarse-backend" -ForegroundColor Yellow
Write-Host "mvn clean compile -DskipTests" -ForegroundColor Yellow
