package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTPlusMinusExprWithRecursionNode extends ASTExprNode {
    ASTExprNode m_operand0;
    ASTPlusMinusExprRecursiveNode m_operand1;

    public ASTPlusMinusExprWithRecursionNode(ASTExprNode operand0, ASTPlusMinusExprRecursiveNode operand1) {
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    public int eval() {
       int operand0 = m_operand0.eval();
       return m_operand1.evalWithOuterOperand(operand0);
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {}
}
