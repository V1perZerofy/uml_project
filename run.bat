@echo off
chcp 65001 >nul
rmdir /s /q out 2>nul
javac -encoding UTF-8 -d out Game.java dungeonadventure\*.java
if errorlevel 1 exit /b 1
java -cp out Game
