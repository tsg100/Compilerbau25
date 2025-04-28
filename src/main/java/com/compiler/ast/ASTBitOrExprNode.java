package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTBitOrExprNode extends ASTExprNode {

    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator;

    public ASTBitOrExprNode(final ASTExprNode operand0, final ASTExprNode operand1, final com.compiler.TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
       final int operand0 = m_operand0.eval();
       final int operand1 = m_operand1.eval();
       return operand0 | operand1;
    }

    public void print(final OutputStreamWriter outStream, final String indent) throws Exception {}
}
