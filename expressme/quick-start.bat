@echo off
echo [INFO] 确保默认JDK版本为JDK6.0及以上版本,已配置JAVA_HOME.

set MVN=mvn
set ANT=ant
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

if exist "tools\maven\apache-maven-3.0.3\" set MVN="%cd%\tools\maven\apache-maven-3.0.3\bin\mvn.bat"
if exist "tools\ant\apache-ant-1.8.2\" set ANT="%cd%\tools\ant\apache-ant-1.8.2\bin\ant.bat"
echo Maven命令为%MVN%
echo Ant命令为%ANT%

echo [Step 1] 安装ExpressMe 所有modules到本地Maven仓库, 为所有项目生成Eclipse项目文件.
cd %~dp0
call %MVN% clean install -Pmodules -Dmaven.test.skip=true
if errorlevel 1 goto error
call %MVN% -Pall eclipse:clean eclipse:eclipse
if errorlevel 1 goto error

echo [Step 2] 启动H2数据库.
cd tools/h2
start "H2" %MVN% exec:java
cd ..\..\

echo [Step 3] 为Showcase 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\showcase
if errorlevel 1 goto error
start "Showcase" %MVN% jetty:run
cd ..\..\

echo [INFO] ExpressMe1.0.0 快速启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8080/showcase

goto end
:error
echo "有错误发生"
:end
pause