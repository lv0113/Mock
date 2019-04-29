@echo off
setlocal enabledelayedexpansion
cd ../
set MAIN_CLASS=com.lvwza.consistent.Application
set CLASS_PATH=-classpath
set BASE_PATH=%CD%
set LIB_HOME=../lib
set BIN_PATH=./
set CONF_PATH=%BASE_PATH%/conf
cd ./lib
for %%i in (*.jar) do set BIN_PATH=!BIN_PATH!;%LIB_HOME%/%%i
cd %CONF_PATH%
echo %CONF_PATH%
echo %BIN_PATH%
java %CLASS_PATH% CONF_PATH;%BIN_PATH% %MAIN_CLASS%
endlocal
pause