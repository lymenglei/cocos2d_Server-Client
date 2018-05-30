@echo off
set ip=127.0.0.1
set port=55533
java -classpath server-client.jar;..\lib\wire-compiler-2.3.0-RC1-jar-with-dependencies.jar ^
network.Client -structName=ABC -structCount=1 -host=%ip% -port=%port%

pause
