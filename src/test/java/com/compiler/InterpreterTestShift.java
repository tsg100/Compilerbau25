package com.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterpreterTestShift extends InterpreterTestBase {

	@Test
    public void testInterpreterShiftLeft() throws Exception
    {
        String program = """
        {
          PRINT 5 << 1;
        }
                """;
        testInterpreter(program, "10\n");
    }
	
	@Test
    public void testInterpreterShiftRight() throws Exception
    {
        String program = """
        {
          PRINT 5 >> 1;
        }
                """;
        testInterpreter(program, "2\n");
    }

}
