package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTReturnStmtNode extends ASTStmtNode {
    ASTExprNode m_expr;
    public ASTReturnStmtNode(ASTExprNode questionMarkExpr) {
        super();
        m_expr = questionMarkExpr;
    }

    @Override
    public void execute(OutputStreamWriter out) {

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
}
