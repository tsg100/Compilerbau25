package com.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestShiftExprParser extends TestExpressionParserBase {

	@Test
	public void testShiftRightExpr() throws Exception {
		assertEquals(5 >> 2, evalExpression("5 >> 2"));
		assertEquals(10 >> 3, evalExpression("10 >> 3"));
		assertEquals(4 >> 1, evalExpression("4 >> 1"));
	}
	
	@Test
	public void testShiftLeftExpr() throws Exception {
		assertEquals(5 << 2, evalExpression("5 << 2"));
		assertEquals(10 << 3, evalExpression("10 << 3"));
		assertEquals(4 << 1, evalExpression("4 << 1"));
	}

}
