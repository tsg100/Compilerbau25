package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTWhileLoopStmtNode extends ASTStmtNode {

    ASTExprNode m_predicate;

    ASTStmtNode m_loopBody;

    public ASTWhileLoopStmtNode(ASTExprNode condition, ASTStmtNode loopBody) {
        this.m_predicate = condition;
        this.m_loopBody = loopBody;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        while (m_predicate.eval() != 0) {
            m_loopBody.execute(out);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "WHILE\n");
        m_predicate.print(outStream, "  " + indent);
        m_loopBody.print(outStream, "  " + indent);
    }
}
