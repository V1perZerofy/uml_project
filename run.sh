#!/usr/bin/env bash
set -e
rm -rf out
javac -encoding UTF-8 -d out Game.java dungeonadventure/*.java
java -cp out Game
