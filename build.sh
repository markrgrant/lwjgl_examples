#!/usr/bin/env bash
if [ "$#" -ne 1 ]; then
    echo "usage: ./build.sh 1"
    exit
fi

javac -g -d classes -sourcepath src -classpath classes:lib/lwjgl/jar/lwjgl.jar src/me/mrg/example/lwjgl/Example$1.java
