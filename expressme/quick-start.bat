@echo off
echo [INFO] ȷ��Ĭ��JDK�汾ΪJDK6.0�����ϰ汾,������JAVA_HOME.

set MVN=mvn
set ANT=ant
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

if exist "tools\maven\apache-maven-3.0.3\" set MVN="%cd%\tools\maven\apache-maven-3.0.3\bin\mvn.bat"
if exist "tools\ant\apache-ant-1.8.2\" set ANT="%cd%\tools\ant\apache-ant-1.8.2\bin\ant.bat"
echo Maven����Ϊ%MVN%
echo Ant����Ϊ%ANT%

echo [Step 1] ��װExpressMe ����modules������Maven�ֿ�, Ϊ������Ŀ����Eclipse��Ŀ�ļ�.
cd %~dp0
call %MVN% clean install -Pmodules -Dmaven.test.skip=true
if errorlevel 1 goto error
call %MVN% -Pall eclipse:clean eclipse:eclipse
if errorlevel 1 goto error

echo [Step 2] ����H2���ݿ�.
cd tools/h2
start "H2" %MVN% exec:java
cd ..\..\

echo [Step 3] ΪShowcase ����Eclipse��Ŀ�ļ�, ����, ���, ��ʼ�����ݿ�, ����Jetty.
cd examples\showcase
if errorlevel 1 goto error
start "Showcase" %MVN% jetty:run
cd ..\..\

echo [INFO] ExpressMe1.0.0 �����������.
echo [INFO] �ɷ���������ʾ��ַ:
echo [INFO] http://localhost:8080/showcase

goto end
:error
echo "�д�����"
:end
pause