#!/bin/bash
set -e
echo "Compiling java code"
javac -Xmaxerrs 1 *.java
echo "Executing code"
java App
echo "Done"
