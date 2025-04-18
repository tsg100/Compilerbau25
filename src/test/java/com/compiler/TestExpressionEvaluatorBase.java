package com.compiler;

public class TestExpressionEvaluatorBase {

    public int evalExpression(String input) throws Exception {
        Lexer lexer = new Lexer();
        ExpressionEvaluatorIntf evaluator = new ExpressionEvaluator(lexer);
        int result = evaluator.eval(input);
        return result;
    }
}
