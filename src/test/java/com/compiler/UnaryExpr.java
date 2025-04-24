package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryExpr extends TestExpressionEvaluatorBase {

    @Test
    public void testMinusOne() throws Exception {
      assertEquals(-1, evalExpression("-1"));
    }

    @Test
    public void testMinusTwo() throws Exception {
      assertEquals(-2, evalExpression("-2"));
    }

    @Test
    public void testFalseNegation() throws Exception {
      assertEquals(1, evalExpression("!0"));
    }

    @Test
    public void testTrueNegation() throws Exception {
        assertEquals(0, evalExpression("!1"));
    }

    @Test
    public void testTrueNegation2() throws Exception {
        assertEquals(0, evalExpression("!2"));
    }

    @Test
    public void testFallthrough() throws Exception {
        assertEquals(8, evalExpression("8"));
    }

}
