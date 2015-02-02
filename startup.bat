echo off
rem setup classpath
echo set _CP=%%_CP%%;%%1> cp.bat
set _CP=.;\classes;"%JAVA_HOME%\lib\dt.jar";"%JAVA_HOME%\lib\tools.jar"
for %%i in (lib\*.jar) do call cp.bat %%i
set CLASSPATH=%_CP%
del cp.bat
echo %CLASSPATH%

rem set JAVA_HOME=
set JAVA_OPTION=-Dfile.encoding=GBK -Xms256m -Xmx256m -XX:MaxPermSize=64m
set RUN_CLASS=com.lxq.batch.Main

"%JAVA_HOME%\bin\java" %JAVA_OPTION% -classpath %CLASSPATH% %RUN_CLASS%

pause