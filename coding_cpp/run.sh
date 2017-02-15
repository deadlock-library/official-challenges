#!/bin/bash
set -e
echo "Compiling C++ code"
make --silent
echo "Running"
./app
