package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class getCompareExprTest extends TestExpressionEvaluatorBase{

    @Test
    public void testCompareExprTest() throws Exception {
        // Check Java Compiler vs. unser Compiler
        Assertions.assertEquals(1, evalExpression("4>1"));
        Assertions.assertEquals(0, evalExpression("4<1"));

        // Check Ergebnis vs. unser Compiler
        Assertions.assertEquals(0, evalExpression("5==2"));
        Assertions.assertEquals(1, evalExpression("2==2"));

        // Komplexere Ausdrücke
        Assertions.assertEquals(1, evalExpression("1>0=0"));
        Assertions.assertEquals(0, evalExpression("1<0=0"));
    }

}
