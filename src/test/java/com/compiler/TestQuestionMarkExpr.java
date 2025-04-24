package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestQuestionMarkExpr extends TestExpressionEvaluatorBase{
    @Test
    public void testQuestionMarkExpr() throws Exception {
      assertEquals(2, evalExpression("0?1:2"));
      assertEquals(1, evalExpression("2?1:2"));
      assertEquals(2, evalExpression("2? 0?1:2 :2"));
    }
}
