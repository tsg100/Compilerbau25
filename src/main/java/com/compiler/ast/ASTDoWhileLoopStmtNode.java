package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTDoWhileLoopStmtNode extends ASTStmtNode {

    ASTExprNode m_predicate;

    ASTStmtNode m_loopBody;

    public ASTDoWhileLoopStmtNode(ASTExprNode condition, ASTStmtNode loopBody) {
        this.m_predicate = condition;
        this.m_loopBody = loopBody;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        do {
            m_loopBody.execute(out);
        } while (m_predicate.eval() != 0);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "DO WHILE\n");
        m_predicate.print(outStream, "  " + indent);
        m_loopBody.print(outStream, "  " + indent);
    }
}
