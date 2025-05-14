package com.compiler.ast;

import com.compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTCompareExprNode extends ASTExprNode{

    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    TokenIntf.Type m_operator;

    public ASTCompareExprNode(ASTExprNode operand0, ASTExprNode operand1, TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
        int operand0 = m_operand0.eval();
        int operand1 = m_operand1.eval();


        return switch (m_operator) {
            case GREATER -> operand0 > operand1 ? 1 : 0;
            case LESS -> operand0 < operand1 ? 1 : 0;
            case EQUAL -> operand0 == operand1 ? 1 : 0;
            default -> 0;
        };
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("CompareExpr ");
        outStream.write(m_operator.toString());
        outStream.write("\n");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }
}
