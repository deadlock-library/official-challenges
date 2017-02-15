#!/bin/bash
set -e
echo "Initializing DB"
./wait-for-it.sh db:5432 -t 120 -s -- java -jar app/target/app-jar-with-dependencies.jar
