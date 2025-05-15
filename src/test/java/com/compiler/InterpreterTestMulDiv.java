package com.compiler;

import org.junit.Test;

public class InterpreterTestMulDiv extends InterpreterTestBase
{
    @Test
    public void testInterpreter() throws Exception
    {
        String program = """
        {
          PRINT 1 + 2;
        }
                """;
        testInterpreter(program, "3\n");
    }

    @Test
    public void testBasicMultiplication() throws Exception
    {
        String program = """
        {
          PRINT 3 * 4;
        }
                """;
        testInterpreter(program, "12\n");
    }

    @Test
    public void testBasicDivision() throws Exception
    {
        String program = """
        {
          PRINT 10 / 2;
        }
                """;
        testInterpreter(program, "5\n");
    }

    @Test
    public void testMultipleMultiplications() throws Exception
    {
        String program = """
        {
          PRINT 2 * 3 * 4;
        }
                """;
        testInterpreter(program, "24\n");
    }

    @Test
    public void testMultipleDivisions() throws Exception
    {
        String program = """
        {
          PRINT 24 / 2 / 3;
        }
                """;
        testInterpreter(program, "4\n");
    }

    @Test
    public void testMixedMultiplicationDivision() throws Exception
    {
        String program = """
        {
          PRINT 10 * 4 / 2;
        }
                """;
        testInterpreter(program, "20\n");
    }

    @Test
    public void testComplexExpression() throws Exception
    {
        String program = """
        {
          PRINT 2 * 3 + 10 / 2;
        }
                """;
        testInterpreter(program, "11\n");
    }

    @Test
    public void testParenthesesPrecedence() throws Exception
    {
        String program = """
        {
          PRINT 2 * (3 + 4);
          PRINT (8 + 4) / 3;
        }
                """;
        testInterpreter(program, "14\n4\n");
    }

    @Test
    public void testComplexMixedOperations() throws Exception
    {
        String program = """
        {
          PRINT 2 * 3 + 4 * 5 / 2 - 3;
          PRINT 10 / 2 * 3 + 4 * 2;
        }
                """;
        testInterpreter(program, "13\n23\n");
    }

    @Test
    public void testWhitespaceHandling() throws Exception
    {
        String program = """
        {
          PRINT 4*3;
          PRINT 10/  2;
          PRINT 2  *  3;
        }
                """;
        testInterpreter(program, "12\n5\n6\n");
    }

    @Test(expected = Exception.class)
    public void testDivisionByZero() throws Exception
    {
        String program = """
        {
          PRINT 5 / 0;
        }
                """;
        testInterpreter(program, "");
    }
}