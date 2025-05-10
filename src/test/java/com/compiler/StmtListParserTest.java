package com.compiler;

import org.junit.Test;

public class StmtListParserTest extends StmtParserTestBase {
    
    @Test
    public void testAndParser() throws Exception {
        final String program = """
                {
                  DECLARE a;
                  a = 2 & 2;
                  PRINT a;
                }
                """;
        testParser(program, "2\n");
    }


    @Test
    public void testOrParser() throws Exception {
        final String program = """
                {
                  DECLARE a;
                  a = 2 | 1;
                  PRINT a;
                }
                """;
        testParser(program, "3\n");
    }


    @Test
    public void testAndOrParser() throws Exception {
        final String program = """
                {
                  DECLARE a;
                  DECLARE b;
                  DECLARE c;
                  a = 2 & 2;
                  b = 1 | 2;
                  c = a & b;
                  PRINT c;
                }
                """;
        testParser(program, "2\n");
    }
}
