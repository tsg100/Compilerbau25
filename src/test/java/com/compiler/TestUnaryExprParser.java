package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUnaryExprParser extends TestExpressionParserBase {

    @Test
    public void NotZero() throws Exception {
        assertEquals(1, evalExpression("!0"));
    }

    @Test
    public void NotOne() throws Exception {
        assertEquals(0, evalExpression("!1"));
    }

    @Test
    public void NotNotOne() throws Exception {
        assertEquals(1, evalExpression("!!1"));
    }

    @Test
    public void NotNotNotOne() throws Exception {
        assertEquals(0, evalExpression("!!!1"));
    }

    @Test
    public void MinusOne() throws Exception {
        assertEquals(-1, evalExpression("-1"));
    }

    @Test
    public void MinusMinusOne() throws Exception {
        assertEquals(1, evalExpression("--1"));
    }
}
