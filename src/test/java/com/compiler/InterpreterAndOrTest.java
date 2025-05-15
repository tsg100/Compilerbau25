package com.compiler;

import org.junit.Test;

public class InterpreterAndOrTest extends InterpreterTestBase {

    @Test
    public void testAndInterpreter() throws Exception {
        final String program = """
                {
                  PRINT 1 && 1;
                }
                """;
        testInterpreter(program, "1\n");
    }


    @Test
    public void testOrInterpreter() throws Exception {
        final String program = """
                {
                  PRINT 0 || 0;
                }
                """;
        testInterpreter(program, "0\n");
    }
}
