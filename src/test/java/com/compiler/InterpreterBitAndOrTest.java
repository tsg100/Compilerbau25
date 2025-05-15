package com.compiler;

import org.junit.Test;

public class InterpreterBitAndOrTest extends StmtParserTestBase {

    @Test
    public void testInterpreter() throws Exception {
        final String program = """
                {
                  PRINT (1 | 2) & 2;
                }
                """;
        testParser(program, "2\n");
    }


    @Test
    public void testInterpreter2() throws Exception {
        final String program = """
                {
                  PRINT 1 | 2 & 2;
                }
                """;
        testParser(program, "3\n");
    }
}
