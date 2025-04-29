package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTDashExpr extends TestExpressionParserBase {
    @Test
    public void testTDash() throws Exception {
        assertEquals((int) Math.pow(16, 2), evalExpression("2^4^2"));
    }
}

