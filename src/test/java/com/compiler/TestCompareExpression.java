package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCompareExpression extends TestExpressionEvaluatorBase {

    @Test
    public void testCompareExpAST() throws Exception {
        assertEquals(1, evalExpression("2>0"));
        assertEquals(1, evalExpression("2>0>-2"));
        assertEquals(0, evalExpression("2>0>2"));
    }

}
