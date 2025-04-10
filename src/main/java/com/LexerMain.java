package com;

import java.io.OutputStreamWriter;

public class LexerMain {

    public static void main(String[] args) throws Exception {
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");

        lexer.processInput("AABB ; //Hello\nBCD", outStream);
    }

}
