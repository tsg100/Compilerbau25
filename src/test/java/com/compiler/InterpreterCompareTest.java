package com.compiler;

import org.junit.Test;

public class InterpreterCompareTest extends InterpreterTestBase{

    @Test
    public void compare() throws Exception {
        final String program = """
                {
                  PRINT 1 > 2;
                }
                """;

        testInterpreter(program, "0\n");
    }

    @Test
    public void compareAgain() throws Exception {
        final String program = """
                {
                  PRINT 4 > 1 == 1 > 0;
                }
                """;

        testInterpreter(program, "1\n");
    }




}
