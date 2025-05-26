package com.compiler;

import org.junit.Test;

public class TestLoopStmt extends InterpreterTestBase {

    @Test
    public void simpleLoopCounter() throws Exception {
        String program = """
            {
            DECLARE a;
            a = 1;
              LOOP{
              PRINT(a);
              a = a + 1;
              IF(a > 10){
              BREAK
              }
              }ENDLOOP
            }
            """;

        testInterpreter(program, "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n");
    }


    @Test
    public void loopceptionTest() throws Exception {
        String program = """
            {
            DECLARE a;
            a = 1;
            DECLARE b;
            b = -1;
              LOOP{
              PRINT(a);
              
                  LOOP{
                  PRINT(b);
                  BREAK
                  }ENDLOOP
              a = a + 1;
              IF(a > 3){
              BREAK
              }
              
              }ENDLOOP
            }
            """;

        testInterpreter(program, "1\n-1\n2\n-1\n3\n-1\n");
    }

}
