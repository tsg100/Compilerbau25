package com.compiler;

import org.junit.Test;

public class InterpreterTestPlusMinus extends StmtParserTestBase
{
    @Test
    public void testInterpreter() throws Exception
    {
        String program = """
        {
          PRINT 1 + 2;
        }
                """;
        testParser(program, "3\n");
    }

}
