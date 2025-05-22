package com.compiler;

import org.junit.Test;

public class StmtNumericIfStmtParserTest extends InterpreterTestBase {

    @Test
    public void testNumericIfProgram01() throws Exception {
        String program = """
                {
                NUMERIC_IF (2)
                POSITIVE {
                    PRINT 1;
                } NEGATIVE {
                    PRINT -1;
                } ZERO {
                    PRINT 0;
                }
                }
                """;
        testInterpreter(program, "1\n");
    }


    @Test
    public void testNumericIfProgram02() throws Exception {
        String program = """
                {
                   DECLARE in;
                   DECLARE out;
                   NUMERIC_IF (in)
                   POSITIVE {
                     out = 1;
                   } NEGATIVE {
                     out = 2;
                   } ZERO {
                     out = 3;
                   }
                   PRINT out;
                 }
                """;
        testInterpreter(program, "3\n");
    }


    @Test
    public void testNumericIfProgram03() throws Exception {
        String program = """
                {
                    DECLARE x;
                    DECLARE y;
                    x = 2;
                    y = 3;
                    NUMERIC_IF (x-y)
                    POSITIVE {
                      y = x - y;
                    } NEGATIVE {
                      y = x + y;
                    } ZERO {
                      y = x * y;
                    }
                    PRINT y;
                  }
                """;
        testInterpreter(program, "5\n");
    }
}
