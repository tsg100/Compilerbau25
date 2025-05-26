package com.compiler;

import org.junit.Test;


public class InterpreterTestIfElse extends InterpreterTestBase {

    @Test
    public void testIfTrue() throws Exception {
        String programm = """
                {
                    IF (4 < 5){
                        PRINT 1;
                    } ELSE {
                        PRINT 42;
                    }
                }
                """;
        String expectedOutput = """
                1
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testIfFalse() throws Exception {
        String programm = """
                {
                    IF (4 > 5){
                        PRINT 1;
                    } ELSE {
                        PRINT 42;
                    }
                }
                """;
        String expectedOutput = """
                42
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testElseIfTrue() throws Exception {
        String programm = """
                {
                    IF (4 > 5){
                        PRINT 1;
                    } ELSE IF (1 < 2) {
                        PRINT 42;
                    }
                }
                """;
        String expectedOutput = """
                42
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testIfTrueWithVariable() throws Exception {
        String programm = """
                {
                    DECLARE favNum;
                    favNum = 5;
                    IF (favNum){
                        PRINT 21;
                    } ELSE {
                        PRINT 42;
                    }
                }
                """;
        String expectedOutput = """
                21
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testElseWithIf() throws Exception {
        String programm = """
                {
                    IF ( 5 < 4 ){
                        PRINT 1;
                    } ELSE {
                        IF (3>9) {
                            PRINT 2;
                        }
                        PRINT 3;
                    }
                }
                """;
        String expectedOutput = """
                3
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testElseIfElse() throws Exception {
        String programm = """
                {
                    IF ( 5 < 4 ){
                        PRINT 1;
                    } ELSE IF( 5 > 9 ){
                        PRINT 2; 
                    } ELSE {
                        PRINT 101;
                    }
                    }
                }
                """;
        String expectedOutput = """
                101
                """;
        testInterpreter(programm, expectedOutput);
    }

   
}
