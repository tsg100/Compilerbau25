package com.compiler.ast;

import com.compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTTDashNode extends ASTExprNode {
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator = TokenIntf.Type.TDASH;

    public ASTTDashNode(ASTExprNode operand0, ASTExprNode operand1) {
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    public int eval() {
       int operand0 = m_operand0.eval();
       int operand1 = m_operand1.eval();
      return (int) Math.pow(operand0,operand1);
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {}
}
