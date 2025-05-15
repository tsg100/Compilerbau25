package com.compiler;

import org.junit.Test;

public class InterpreterTestTDash extends InterpreterTestBase
{
    @Test
    public void testInterpreter() throws Exception
    {
        String program = """
        {
          PRINT 2 ^ 1;
        }
                """;
        testInterpreter(program, "2\n");
    }

}
