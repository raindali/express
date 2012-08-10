@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
call mvn clean install -Pmodules -Dmaven.test.skip=true
pause