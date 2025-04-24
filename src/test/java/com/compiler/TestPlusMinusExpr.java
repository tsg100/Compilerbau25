package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPlusMinusExpr extends TestExpressionEvaluatorBase {

    @Test
    public void testPlusMinusExpr() throws Exception {
      assertEquals(5+42-19, evalExpression("5+42-19"));
    }

}
