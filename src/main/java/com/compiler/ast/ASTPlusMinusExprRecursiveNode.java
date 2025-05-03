package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTPlusMinusExprRecursiveNode extends ASTExprNode {
    com.compiler.TokenIntf.Type m_operator;
    ASTExprNode m_operand0;
    ASTPlusMinusExprRecursiveNode m_operand1;

    public ASTPlusMinusExprRecursiveNode(com.compiler.TokenIntf.Type operator, ASTExprNode operand0, ASTPlusMinusExprRecursiveNode operand1) {
        m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    public int eval() {
        int operand0 = m_operand0.eval();
        if (m_operand1 != null) {
            return m_operand1.evalWithOuterOperand(operand0);
        } else {
            return operand0;
        }
    }

    public int evalWithOuterOperand(int outerOperand) {
        int innerOperand = eval();
        if (m_operator == com.compiler.TokenIntf.Type.PLUS) {
            return outerOperand + innerOperand;
        } else {
            return outerOperand - innerOperand;
        }
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {}
}
