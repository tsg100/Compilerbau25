package com.compiler.ast;

import com.compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTUnaryExprNode extends ASTExprNode {
    private final ASTExprNode m_operand;
    private final TokenIntf.Type m_op;

    public ASTUnaryExprNode(ASTExprNode operand, TokenIntf.Type op) {
        m_operand = operand;
        m_op = op;
    }

    @Override
    public String toString() {
        return "UnaryExpr: " + m_op + " " + m_operand.toString();
    }

    @Override
    public int eval() {
        int operandValue = m_operand.eval();
        return switch (m_op) {
            case MINUS -> -operandValue;
            case NOT -> operandValue == 0 ? 1 : 0;
            default -> throw new RuntimeException("Unknown unary operator: " + m_op);
        };
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + this + "\n");
    }
}
