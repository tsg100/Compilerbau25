package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTMulDivExprNode extends ASTExprNode {
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator;

    public ASTMulDivExprNode(ASTExprNode operand0, ASTExprNode operand1, com.compiler.TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
        int operand0 = m_operand0.eval();
        int operand1 = m_operand1.eval();
        if (m_operator == com.compiler.TokenIntf.Type.MUL) {
            return operand0 * operand1;
        } else if (m_operator == com.compiler.TokenIntf.Type.DIV) {
            return operand0 / operand1;
        } else {
            // Would have liked to throw an Exception here
            return 0;
        }
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {

        m_operand0.print(outStream, indent);

        outStream.write(indent + getStringReprOfOperator(m_operator) + " ");

        m_operand1.print(outStream, indent);

    }

    private String getStringReprOfOperator(com.compiler.TokenIntf.Type operator) throws Exception {
        if (m_operator == com.compiler.TokenIntf.Type.MUL) {
            return "*";
        } else if (m_operator == com.compiler.TokenIntf.Type.DIV) {
            return "/";
        } else {
            throw new Exception("Expected operator type MUL or DIV, got " + operator);
        }

    }
}
