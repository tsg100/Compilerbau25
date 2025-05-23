package com.compiler;

import org.junit.Test;

public class InterpreterTestFunctionStmts extends InterpreterTestBase {

    @Test
    public void testProgrammSimple() throws Exception {
        String programm = """
                {
                    FUNCTION minüß3(x) {
                        EXECUTE 3 TIMES {
                            x = x - 1;
                        };
                        RETURN x;
                    };
                    PRINT CALL minüß3(45);
                }
                """;
        String expectedOutput = """
                42
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testFunction1NoParams() throws Exception {
        String programm = """
                {
                    FUNCTION myFct() {
                        RETURN 1;
                    };
                    PRINT CALL myFct();
                }
                """;
        String expectedOutput = """
                1
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testFunction2WithParams() throws Exception {
        String programm = """
                {
                    FUNCTION myFct(a, b) {
                        RETURN a + b;
                    };
                    PRINT CALL myFct(1, 2);
                }
                """;
        String expectedOutput = """
                3
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testFunction3MultipleCalls() throws Exception {
        String programm = """
                {
                    FUNCTION myFct1(a, b) {
                        DECLARE c;
                        c = a + 2 * b;
                        RETURN c;
                    };
                    FUNCTION myFct2(d) {
                        RETURN d + 1;
                    };
                    DECLARE e;
                    e = CALL myFct1(1, 2) + CALL myFct2(3);
                    PRINT e;
                }
                """;
        String expectedOutput = """
                9
                """;
        testInterpreter(programm, expectedOutput);
    }

    @Test
    public void testFunction4CallInReturn() throws Exception {
        String programm = """
                {
                    FUNCTION myFct1(a, b) {
                        RETURN a * b;
                    };
                    FUNCTION myFct2(c) {
                        RETURN CALL myFct1(c, c + 1);
                    };
                    PRINT CALL myFct2(5);
                }
                """;
        String expectedOutput = """
                30
                """;
        testInterpreter(programm, expectedOutput);
    }
}
