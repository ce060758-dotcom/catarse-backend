# ============================================
# BACKUP COMPLETO CATARSE BACKEND
# ============================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "         BACKUP CATARSE BACKEND            " -ForegroundColor Yellow
Write-Host "============================================" -ForegroundColor Cyan

# 1. CONFIGURAÇÕES
$backupRoot = "C:\backups\catarse"
$dateStr = Get-Date -Format "yyyy-MM-dd_HH-mm"
$backupDir = "$backupRoot\$dateStr"

# 2. CRIA DIRETÓRIOS
Write-Host "`n[1/4] Criando estrutura de diretórios..." -ForegroundColor Green
New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
New-Item -ItemType Directory -Path "$backupDir\codigo" -Force | Out-Null
New-Item -ItemType Directory -Path "$backupDir\database" -Force | Out-Null
New-Item -ItemType Directory -Path "$backupDir\config" -Force | Out-Null

# 3. BACKUP DO CÓDIGO FONTE
Write-Host "[2/4] Fazendo backup do código fonte..." -ForegroundColor Green
$projectPath = "C:\Users\55119\catarse-backend"

# Copia tudo exceto diretórios grandes
Copy-Item -Path "$projectPath\*" -Destination "$backupDir\codigo\" -Recurse `
    -Exclude @(
        "target", 
        ".git", 
        "*.log", 
        "*.iml", 
        "*.jar",
        "node_modules",
        "backup-*",
        "*.zip"
    )

# 4. BACKUP DO MONGODB
Write-Host "[3/4] Fazendo backup do MongoDB..." -ForegroundColor Green
try {
    # Verifica se MongoDB está rodando
    $mongoProcess = Get-Process -Name "mongod" -ErrorAction SilentlyContinue
    
    if ($mongoProcess) {
        # Executa mongodump
        & "C:\Program Files\MongoDB\Server\*\bin\mongodump.exe" `
            --host localhost `
            --port 27017 `
            --db catarse `
            --out "$backupDir\database"
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "   ✓ MongoDB backup realizado com sucesso!" -ForegroundColor Green
        } else {
            Write-Host "   ✗ Falha no backup MongoDB" -ForegroundColor Red
        }
    } else {
        Write-Host "   ⚠ MongoDB não está rodando. Backup do banco ignorado." -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ⚠ Erro ao fazer backup MongoDB: $_" -ForegroundColor Yellow
}

# 5. BACKUP DE CONFIGURAÇÕES IMPORTANTES
Write-Host "[4/4] Fazendo backup de configurações..." -ForegroundColor Green

# Application properties (se existir)
if (Test-Path "$projectPath\src\main\resources\application.properties") {
    Copy-Item -Path "$projectPath\src\main\resources\application.properties" `
              -Destination "$backupDir\config\application.properties.backup"
}

# Maven settings
if (Test-Path "$env:USERPROFILE\.m2\settings.xml") {
    Copy-Item -Path "$env:USERPROFILE\.m2\settings.xml" `
              -Destination "$backupDir\config\maven-settings.xml"
}

# 6. COMPACTA TUDO
Write-Host "`n[FINAL] Compactando backup..." -ForegroundColor Cyan
$zipFile = "$backupRoot\catarse-backup-$dateStr.zip"
Compress-Archive -Path "$backupDir\*" -DestinationPath $zipFile -Force

# 7. LIMPA DIRETÓRIO TEMPORÁRIO
Remove-Item -Path $backupDir -Recurse -Force -ErrorAction SilentlyContinue

# 8. RELATÓRIO FINAL
Write-Host "`n" + "="*50 -ForegroundColor Cyan
Write-Host "✅ BACKUP CONCLUÍDO COM SUCESSO!" -ForegroundColor Green
Write-Host "="*50 -ForegroundColor Cyan

$zipInfo = Get-Item $zipFile
$sizeMB = [math]::Round($zipInfo.Length / 1MB, 2)

Write-Host "`n📁 Arquivo: $zipFile" -ForegroundColor White
Write-Host "📊 Tamanho: $sizeMB MB" -ForegroundColor White
Write-Host "🕐 Data: $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')" -ForegroundColor White

# 9. VERIFICA SE TEM GIT CONFIGURADO
Write-Host "`n🔍 Verificando Git..." -ForegroundColor Cyan
try {
    $gitStatus = git status 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "   ✓ Git está configurado neste diretório" -ForegroundColor Green
        
        # Mostra status atual
        Write-Host "`n📈 Status do Git:" -ForegroundColor Yellow
        git status --short
    } else {
        Write-Host "   ⚠ Git não está inicializado ou configurado" -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ⚠ Não foi possível verificar Git" -ForegroundColor Yellow
}

Write-Host "`n🎯 Próximos passos:" -ForegroundColor Magenta
Write-Host "   1. Commit no GitHub: git add . && git commit -m 'backup' && git push"
Write-Host "   2. Teste a API novamente"
Write-Host "   3. Configure backup automático se necessário"
Write-Host "`nPressione qualquer tecla para continuar..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
