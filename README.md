# README

The purpose of this library is to adapt the examples from the OpenGL
Superbible to Java using lwjgl, a wrapper library that provides access
to both GLFW (a very nice C library for creating windows with OpenGL contexts)
and modern OpenGL.  The OpenGL Superbible is a thorough resource but it
can take some time to get an example written in C working using lwjgl in Java,
particularly with a beginner's understanding of OpenGL and without access
to a translation of the C++ framework they inherit from in their examples.

Not all examples have been translated yet.

## Setup

Download the lwjgl.zip from ``https://www.lwjgl.org/download.php`` and unzip
it in a directory named ``lib`` in the root folder of the project.  It
contains the necessary jar file and native libraries.

## Building

run ``./build.sh <num>`` where <num> is the number of the example to build.

## Running

run ``./build.sh <num>`` where <num> is the number of the example to run.

## Debugging

run ``./debug.sh <num>`` where <num> is the number of th example to debug.


## TODO

Use platform-agnostic build tool. 
