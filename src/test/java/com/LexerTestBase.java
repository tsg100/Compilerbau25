package com;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class LexerTestBase {
    protected void testLexer(String input, String expectedOutput) throws Exception {
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter outStream = new OutputStreamWriter(os);        
        lexer.processInput(input, outStream);
        outStream.flush();
        String output = os.toString();
        assertEquals(expectedOutput, output);
    }

}
