package com.compiler;

import org.junit.Test;

public class StmtParserTest extends StmtParserTestBase
{
    @Test
    public void testParser() throws Exception
    {
        String program = """
        {
          DECLARE a;
          DECLARE b;
          a = 1 + 2;
          b = 5;
          PRINT a ? b + 1 : -1;
          PRINT 1 + 2;
          PRINT 3 + 4;
        }
                """;
        testParser(program, "6\n3\n7\n");
    }

    @Test
    public void testParserGroupA() throws Exception {
        // com.compiler.Lexer lexer = new com.compiler.Lexer();
        // com.compiler.StmtParser parser = new com.compiler.StmtParser(lexer);
        // String program = """
        //         {
        //         PRINT 0 ? 2: 1;
        //         PRINT 2 ? 2: 1;
        //         }
        //         """;
        // String expOutput = "1\n2\n";
        // testParser(program, expOutput);
    }
}
