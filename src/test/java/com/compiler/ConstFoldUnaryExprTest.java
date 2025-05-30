package com.compiler;

import org.junit.Test;

public class ConstFoldUnaryExprTest extends InterpreterDumpTestBase {
    @Test
    public void testUnaryMinus() throws Exception {
        String input = """
                {
                    PRINT -5;
                }
                """;
        String expected = """
entry:
%0 = LITERAL -5
PRINT %0

""";
        testInterpreter(input, expected);
    }

    @Test
    public void testNotZero() throws Exception {
        String input = """
                {
                    PRINT !0;
                }
                """;
        String expected = """
entry:
%0 = LITERAL 1
PRINT %0

""";
        testInterpreter(input, expected);

    }

}
