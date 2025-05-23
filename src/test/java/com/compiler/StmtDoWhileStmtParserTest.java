package com.compiler;

import org.junit.Test;

public class StmtDoWhileStmtParserTest extends InterpreterTestBase {
  @Test
  public void testWhileProgram1() throws Exception {
    String program = """
        {
          DECLARE index;
          DECLARE sum;
          index = 10;
          sum = 0;
          WHILE(index) {
            sum = sum + index;
            index = index - 1;
          };
          PRINT sum;
        }
        """;
    // laut kleinem Gauß: 55
    testInterpreter(program, "55\n");
  }

  @Test
  public void testDoWhileProgram2() throws Exception {
    String program = """
        {
          DECLARE index;
          DECLARE sum;
          index = 10;
          sum = 0;
          DO {
            sum = sum + index;
            index = index - 1;
          } WHILE(index);
          PRINT sum;
        }
        """;
    testInterpreter(program, "55\n");
  }

  @Test
  public void testNestedWhileProgram3() throws Exception {
    String program = """
        {
          DECLARE index0;
          DECLARE sum;
          DECLARE index1;
          index0 = 10;
          sum = 0;
          WHILE(index0) {
            index1 = 5;
            WHILE(index1) {
              sum = sum + index1;
              index1 = index1 - 1;
            };
            index0 = index0 - 1;
          };
          PRINT sum;
        }
        """;
    testInterpreter(program, "150\n");
  }

  @Test
  public void testNestedDoWhileProgram4() throws Exception {
    String program = """
        {
          DECLARE index0;
          DECLARE sum;
          DECLARE index1;
          index0 = 10;
          sum = 0;
          DO {
            index1 = 5;
            DO {
              sum = sum + index1;
              index1 = index1 - 1;
            } WHILE(index1);
            index0 = index0 - 1;
          } WHILE(index0) ;
          PRINT sum;
        }
        """;
    testInterpreter(program, "150\n");
  }
}
