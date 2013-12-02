#!/bin/sh

set SVC_HOME=..
set FWK_HOME=..\..\..\framework
set SVC_CLASS_PATH=.;%SVC_HOME%\lib;%FWK_HOME%\bin;%FWK_HOME%\lib

set SVC_NAME=alertserver
set SVC_EXE_JAR=%SVC_NAME%.jar
set SVC_MAIN_CLASS=com.chinawiserv.service.alertserver.Main
set SVC_CONF_FILE=%SVC_NAME%.properties

echo ***********************************************************************
echo   JAVA_HOME=%JAVA_HOME%
echo   SVC_NAME=%SVC_NAME%
echo   FWK_HOME=%FWK_HOME%
echo   SVC_CONF_FILE=%SVC_CONF_FILE%
echo   SVC_CLASS_PATH=%SVC_CLASS_PATH%
echo ***********************************************************************
echo 
echo   Starting %SVC_NAME% ...

%JAVA_HOME%\bin\java -Djava.ext.dirs=%SVC_CLASS_PATH% %SVC_MAIN_CLASS% -c %SVC_CONF_FILE% 

pause