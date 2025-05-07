package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTShiftExprNode extends ASTExprNode{
	
	ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator;

    public ASTShiftExprNode(ASTExprNode operand0, ASTExprNode operand1, com.compiler.TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
       int operand0 = m_operand0.eval();
       int operand1 = m_operand1.eval();
       if (m_operator == com.compiler.TokenIntf.Type.SHIFTLEFT) {
          return operand0 << operand1;
       } else {
    	  return operand0 >> operand1;
       }
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {}
}
