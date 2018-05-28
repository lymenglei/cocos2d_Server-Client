@echo off

:: Redis for windows install path
set Redis_dir="C:\Program Files\Redis"

%Redis_dir%\redis-server.exe %Redis_dir%\redis.windows.conf

pause


:::::::::::::::::::::::::::::::::::::::::
:: check on Windows
:: netstat -ano | findstr 6379
:: tasklist | findstr 2356


:: FOR EXAMPLE
::C:\Users\menglei>netstat -ano | findstr 6379
::  TCP    127.0.0.1:6379         0.0.0.0:0              LISTENING       2356

::C:\Users\menglei>tasklist | findstr 2356
::redis-server.exe              2356 Services                   0     22,792 K