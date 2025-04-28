package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBitAndOrExprParser extends TestExpressionParserBase {

    @Test
    public void testBitAndOrExpr() throws Exception {
        // Check Java Compiler vs. unser Compiler
        assertEquals(4|1, evalExpression("4|1"));
        assertEquals(4&1, evalExpression("4&1"));

        // Check Ergebnis vs. unser Compiler
        assertEquals(5, evalExpression("4|1"));
        assertEquals(0, evalExpression("4&1"));

        // Komplexere Ausdrücke
        assertEquals(4&3|1&5, evalExpression("4&3|1&5"));
        assertEquals(1|2&3|7, evalExpression("1|2&3|7"));

        // Überprüfung, das & eine höhere Priorität als | hat.
        assertEquals(5 & 4 | 3, evalExpression("5 & 4 | 3"));
        assertEquals(5 | 4 & 3, evalExpression("5 | 4 & 3"));
    }
}
