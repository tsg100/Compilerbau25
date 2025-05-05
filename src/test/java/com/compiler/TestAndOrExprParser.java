package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestAndOrExprParser extends TestExpressionParserBase {

    @Test
    public void testPriorityAndOverOr() throws Exception {
        assertEquals(true || false && false ? 1 : 0, evalExpression("1||0&&0"));
    }

    @Test
    public  void  testAndExpr() throws Exception {
        assertEquals(false && false ? 1 : 0, evalExpression("0&&0"));
        assertEquals(false && true ? 1 : 0, evalExpression("0&&1"));
        assertEquals(true && false ? 1 : 0, evalExpression("1&&0"));
        assertEquals(true && true ? 1 : 0, evalExpression("1&&1"));
    }

    @Test
    public  void  testOrExpr() throws Exception {
        assertEquals(false || false ? 1 : 0, evalExpression("0||0"));
        assertEquals(false || true ? 1 : 0, evalExpression("0||1"));
        assertEquals(true || false ? 1 : 0, evalExpression("1||0"));
        assertEquals(true || true ? 1 : 0, evalExpression("1||1"));
    }

}
