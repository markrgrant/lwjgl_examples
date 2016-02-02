#!/usr/bin/env bash
if [ "$#" -ne 1 ]; then
    echo "usage: ./run.sh 1"
    exit
fi

java -Djava.library.path=lib/lwjgl/native -classpath classes:lib/lwjgl/jar/lwjgl.jar me.mrg.example.lwjgl.Example$1
