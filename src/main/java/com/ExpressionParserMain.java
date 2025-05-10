package com;

import java.io.OutputStreamWriter;

public class ExpressionParserMain {

    public static void main(String[] args) throws Exception {
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        com.compiler.ExpressionParser parser = new com.compiler.ExpressionParser(lexer);
        com.compiler.ast.ASTExprNode expr = parser.parseExpression("30 + 12 - 5");
        int result = expr.eval();
        System.out.println(result);
        OutputStreamWriter out = new OutputStreamWriter(System.out);
        expr.print(out, "");
        out.flush();
    }

}
