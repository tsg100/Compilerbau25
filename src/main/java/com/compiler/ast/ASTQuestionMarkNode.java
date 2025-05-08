package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTQuestionMarkNode extends ASTExprNode {
    ASTExprNode m_predicate;
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;

    public ASTQuestionMarkNode(ASTExprNode predicate, ASTExprNode operand0, ASTExprNode operand1) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_predicate = predicate;
    }

    public int eval() {
        return (m_predicate.eval() != 0) ? m_operand0.eval() : m_operand1.eval();
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("QuestionMarkExpr ");
        outStream.write("\n");
        m_predicate.print(outStream, indent + "  ");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }
}
