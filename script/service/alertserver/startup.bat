@echo off

set SVC_HOME=..
set FWK_HOME=..\..\..\framework
set SVC_CLASS_PATH=.;%SVC_HOME%\lib;%FWK_HOME%\bin;%FWK_HOME%\lib;%JAVA_HOME%\lib;%JAVA_HOME%\jre\lib;%JAVA_HOME%\jre\lib\ext

set SVC_NAME=alertserver
set SVC_EXE_JAR=%SVC_NAME%.jar
set SVC_MAIN_CLASS=com.chinawiserv.service.alertserver.Main
set SVC_CONF_FILE=%SVC_HOME%\conf\%SVC_NAME%.properties
set LOG_CONF_FILE=file:%SVC_HOME%\conf\log4j.properties

set JVM_OPTIONS=-Djava.ext.dirs="%SVC_CLASS_PATH%" -Dlog4j.configuration="%LOG_CONF_FILE%"
set CLASS_OPTIONS=-c "%SVC_CONF_FILE%"

echo Starting %SVC_NAME% ...
echo ***********************************************************************
echo   JAVA_HOME=%JAVA_HOME%
echo   SVC_NAME=%SVC_NAME%
echo   FWK_HOME=%FWK_HOME%
echo   SVC_CONF_FILE=%SVC_CONF_FILE%
echo   LOG_CONF_FILE=%LOG_CONF_FILE%
echo   SVC_CLASS_PATH=%SVC_CLASS_PATH%
echo ***********************************************************************

"%JAVA_HOME%\bin\java" %JVM_OPTIONS% %SVC_MAIN_CLASS% %CLASS_OPTIONS%

pause