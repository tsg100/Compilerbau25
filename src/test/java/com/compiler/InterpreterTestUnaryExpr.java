package com.compiler;

import org.junit.Test;

public class InterpreterTestUnaryExpr extends  StmtParserTestBase {
    @Test
    public void testMinusOne() throws Exception
    {
        String program = """
        {
          PRINT -1;
        }
        """;
        testParser(program, "-1\n");
    }

    @Test
    public void testNotOne() throws Exception
    {
        String program = """
        {
          PRINT !1;
        }
        """;
        testParser(program, "0\n");
    }

    @Test
    public void testMinusZero() throws Exception
    {
        String program = """
        {
          PRINT -0;
        }
        """;
        testParser(program, "0\n");
    }

    @Test
    public void testNotZero() throws Exception
    {
        String program = """
        {
          PRINT !0;
        }
        """;
        testParser(program, "1\n");
    }

}
