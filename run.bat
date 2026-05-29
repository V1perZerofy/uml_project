@echo off
chcp 65001 >nul
javac -encoding UTF-8 -d out *.java
if errorlevel 1 exit /b 1
java -cp out Game
