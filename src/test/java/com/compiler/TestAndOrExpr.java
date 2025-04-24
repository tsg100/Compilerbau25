package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestAndOrExpr extends TestExpressionEvaluatorBase {

    @Test
    public void testPlusMinusExpr() throws Exception {
        assertEquals(true || false && false ? 1 : 0, evalExpression("1||0&&0"));
    }

}
