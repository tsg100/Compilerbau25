package com.compiler;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class StmtParserTestBase {
    protected void testParser(String program, String expectedOutput) throws Exception {
        // create out stream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter outStream = new OutputStreamWriter(os);
        // create parser
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        StmtParser parser = new StmtParser(lexer);
        // parse program
        com.compiler.ast.ASTStmtNode rootNode = parser.parseProgram(program);
        // execute
        rootNode.execute(outStream);
        // check result
        outStream.flush();
        String output = os.toString();
        assertEquals(expectedOutput, output);
    }

}
