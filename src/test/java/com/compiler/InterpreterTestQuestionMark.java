package com.compiler;

import org.junit.Test;

public class InterpreterTestQuestionMark extends InterpreterTestBase {
    @Test
    public void testQuestionMark() throws Exception {
        String program = """
                {
                    PRINT 0? 1: 2 ? 1: 2;
                    PRINT 1? 5: 2;
                }
                """;
        String expectedOutput = """
                1
                5
                """;
        
        testInterpreter(program, expectedOutput);
    }
}
