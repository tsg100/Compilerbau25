
package com.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMulDivExpressionParser extends TestExpressionParserBase {

  @Test
  public void testMulDivExpression() throws Exception {
    assertEquals(4 * 5 / 2, evalExpression("4 * 5 / 2"));
  }

  @Test
  public void testSimpleMultiplication() throws Exception {
    assertEquals(6, evalExpression("2 * 3"));
  }

  @Test
  public void testSimpleDivision() throws Exception {
    assertEquals(2, evalExpression("8 / 4"));
  }

  @Test
  public void testMultiplicationAndDivision() throws Exception {
    assertEquals(10, evalExpression("20 / 2 * 1"));
  }

  @Test
  public void testMultiplicationWithZero() throws Exception {
    assertEquals(0, evalExpression("0 * 100"));
  }

  @Test
  public void testDivisionByOne() throws Exception {
    assertEquals(7, evalExpression("7 / 1"));
  }

  @Test
  public void testMultipleOperators() throws Exception {
    assertEquals(6, evalExpression("2 * 3 * 1"));
  }

  @Test
  public void testPrecedenceWithAddition() throws Exception {
    assertEquals(14, evalExpression("2 + 3 * 4"));
  }

  @Test
  public void testDivisionResultTruncation() throws Exception {
    assertEquals(2, evalExpression("5 / 2"));
  }

}
