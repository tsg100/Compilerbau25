package com.compiler.ast;


import java.io.IOException;
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
            out.write(result + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTPrintStmtNode ");
        outStream.write("\n");
        astExprNode.print(outStream, indent + "  ");
    }
}
