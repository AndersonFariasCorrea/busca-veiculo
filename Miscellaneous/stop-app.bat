@echo off

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr /i "listening"') do (set "pid=%%a")
if defined pid (
    echo Stopping process on port 8080 with PID %pid%...
    taskkill /f /pid %pid%
    echo Process stopped successfully.
) else (
    echo No process found on port 8080.
)


set "JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -Dconsole.encoding=UTF-8"
echo Setting JAVA_TOOL_OPTIONS environment variable...
setx JAVA_TOOL_OPTIONS "%JAVA_TOOL_OPTIONS%"
echo JAVA_TOOL_OPTIONS environment variable set successfully.
