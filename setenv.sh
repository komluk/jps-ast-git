#!/bin/sh
function runAnt() {
    ant
    file=/home/pawel/Dropbox/Documents/pjwstk/3/jps/workspace/ast/src/main/java/pl/edu/pjwstk/jps/parser/Lexer.java
    iconv -c -f utf-8 -t ascii $file > $file.clean
    mv $file.clean $file
}