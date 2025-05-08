package com.compiler;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class TestVariableExpr {

    @Test
    public void variableNotDeclared() throws Exception {
        Lexer lexer = new com.compiler.Lexer();
        ExpressionParser parser = new com.compiler.ExpressionParser(lexer);
        assertThrows(CompilerException.class, () -> parser.parseExpression("b = a + 1"));
    }
    
}
