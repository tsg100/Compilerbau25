package com.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterpreterTestExecuteNTimes extends InterpreterTestBase {

	@Test
    public void testSimpleExecute() throws Exception {
        String program = """
                {
                  EXECUTE 3 TIMES {
                  	PRINT 3;
                  };
                }
                """;
        String expectedOutput = """
                3
                3
                3
                """;
        testInterpreter(program, expectedOutput);
    }
	
	@Test
	public void testProgramm1() throws Exception {
		String program = """
                {
				  DECLARE index;
				  DECLARE sum;
				  DECLARE x;
				  sum = 0;
				  index = 0;
				  x = 10;
				  EXECUTE x TIMES {
				  	sum = sum + index;
					  index = index + 1;
				  };
				  PRINT index;
				  PRINT sum;
				}
                """;
		String expectedOutput = """
                10
                45
                """;
		testInterpreter(program, expectedOutput);
	}

	@Test
	public void testProgramm2() throws Exception {
		String program = """
			{
					DECLARE index;
					DECLARE innerIndex;
					DECLARE sum;
					DECLARE x;
					sum = 0;
					index = 0;
					x = 10 + 1 - 1;
					EXECUTE x TIMES {
						innerIndex = 0;
						EXECUTE index TIMES {
							sum = sum + innerIndex;
							innerIndex = innerIndex + 1;
						};
						index = index + 1;
					};
					PRINT index;
					PRINT innerIndex;
					PRINT sum;
			}
		""";
		String expectedOutput = """
                10
                9
                120
                """;
		testInterpreter(program, expectedOutput);
	}
}

