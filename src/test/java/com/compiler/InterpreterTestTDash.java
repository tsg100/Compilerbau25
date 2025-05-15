package com.compiler;

import org.junit.Test;

public class InterpreterTestTDash extends StmtParserTestBase
{
    @Test
    public void testInterpreter() throws Exception
    {
        String program = """
        {
          PRINT 2 ^ 1;
        }
                """;
        testParser(program, "2\n");
    }

}
