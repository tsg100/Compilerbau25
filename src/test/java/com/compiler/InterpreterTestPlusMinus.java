package com.compiler;

import org.junit.Test;

public class InterpreterTestPlusMinus extends InterpreterTestBase
{
    @Test
    public void testInterpreter() throws Exception
    {
        String program = """
        {
          PRINT 1 + 2;
          PRINT 4 + 5 + 6;
        }
                """;
        testInterpreter(program, "3\n15\n");
    }

}
