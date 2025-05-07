package com.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestShiftExpr extends TestExpressionEvaluatorBase {

	@Test
	public void testShiftRight() throws Exception {
		assertEquals(5 >> 2, evalExpression("5 >> 2"));
	}
	
	@Test
	public void testShiftLeft() throws Exception {
		assertEquals(5 << 2, evalExpression("5 << 2"));
	}

}
