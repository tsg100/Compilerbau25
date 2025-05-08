package com.compiler.ast;

import java.io.IOException;
import java.io.OutputStreamWriter;

import com.compiler.TokenIntf;

public class ASTPrintStmtNode extends ASTStmtNode {
    ASTExprNode m_expr;
    TokenIntf.Type m_operator = TokenIntf.Type.PRINT;

    public ASTPrintStmtNode(ASTExprNode expr) {
        m_expr = expr;
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
    }

    @Override
    public void execute(OutputStreamWriter out) {
        int expr = m_expr.eval();
        System.out.println(expr);
        try {
            out.write(expr);
        } catch (IOException ignore) {}
    }
}
