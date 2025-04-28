package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPlusMinusExprParser extends TestExpressionParserBase {

    @Test
    public void testPlusMinusExpr() throws Exception {
      assertEquals(5 - 3 - 2, evalExpression("5 -3-2"));
    }

}
