package com.compiler;

import org.junit.Test;

public class InterpreterBitAndOrTest extends InterpreterTestBase {

    @Test
    public void testInterpreter() throws Exception {
        final String program = """
                {
                  PRINT (1 | 2) & 2;
                }
                """;
        testInterpreter(program, "2\n");
    }


    @Test
    public void testInterpreter2() throws Exception {
        final String program = """
                {
                  PRINT 1 | 2 & 2;
                }
                """;
        testInterpreter(program, "3\n");
    }
}
