$pid = (netstat -ano | findstr :8080 | Select-String -Pattern "LISTENING\s+(\d+)" | ForEach-Object { $_.Matches[0].Groups[1].Value }).Trim()

if ($pid) {
    Stop-Process -Id $pid -Force
    Write-Host "Process on port 8080 with PID $pid stopped successfully."
} else {
    Write-Host "No process found on port 8080."
}
