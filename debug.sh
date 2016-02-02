#!/usr/bin/env bash
if [ "$#" -ne 1 ]; then
    echo "usage: ./debug.sh 1"
    exit
fi

jdb -Djava.library.path=lib/lwjgl/native -sourcepath src -classpath classes:lib/lwjgl/jar/lwjgl.jar me.mrg.example.lwjgl.Example$1
