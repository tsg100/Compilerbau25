package com.compiler;

public class TestExpressionParserBase {

    public int evalExpression(String input) throws Exception {
        Lexer lexer = new Lexer();
        ExpressionParser parser = new ExpressionParser(lexer);
        com.compiler.ast.ASTExprNode expr = parser.parseExpression(input);
        int result = expr.eval();
        return result;
    }
}
