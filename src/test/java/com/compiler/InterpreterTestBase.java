package com.compiler;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class InterpreterTestBase {
    protected void testInterpreter(String program, String expectedOutput) throws Exception {
        // create out stream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter outStream = new OutputStreamWriter(os);
        // compile program
        com.compiler.CompileEnv compileEnv = new com.compiler.CompileEnv(program, false);
        compileEnv.compile();
        // execute
        compileEnv.execute(outStream);
        // check result
        outStream.flush();
        String output = os.toString();
        assertEquals(expectedOutput, output);
    }

}
