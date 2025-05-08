package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTPrintStmtNode extends ASTStmtNode{

    private final ASTExprNode astExprNode;

    public ASTPrintStmtNode(ASTExprNode astExprNode) {
        this.astExprNode = astExprNode;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        String result = Integer.toString(astExprNode.eval());
        try {
            print(out, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
    }
}
