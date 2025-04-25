package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetCompareExprTest extends TestExpressionEvaluatorBase{

    @Test
    public void TestCompareExprTest() throws Exception {
        // Check Java Compiler vs. unser Compiler
        assertEquals(1, evalExpression("4>1"));
        assertEquals(0, evalExpression("4<1"));

        // Check Ergebnis vs. unser Compiler
        assertEquals(0, evalExpression("5==2"));
        assertEquals(1, evalExpression("2==2"));

        // Komplexere Ausdrücke
        assertEquals(1, evalExpression("1>0=0"));
        assertEquals(0, evalExpression("1<0=0"));
    }

}
