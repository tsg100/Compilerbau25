package com.compiler.ast;

import com.compiler.InstrIntf;
import com.compiler.TokenIntf;
import com.compiler.instr.InstrIntegerLiteral;
import com.compiler.instr.InstrUnaryExpr;

import java.io.OutputStreamWriter;

public class ASTUnaryExprNode extends ASTExprNode {
    private final ASTExprNode m_operand;
    private final TokenIntf.Type m_operator;
    private Integer m_constValue = null;

    public ASTUnaryExprNode(ASTExprNode operand, TokenIntf.Type op) {
        m_operand = operand;
        m_operator = op;
    }

    @Override
    public String toString() {
        return "UnaryExpr: " + m_operator + " " + m_operand.toString();
    }

   @Override
    public int eval() {
        int operandValue = m_operand.eval();
        return switch (m_operator) {
            case MINUS -> -operandValue;
            case NOT -> operandValue == 0 ? 1 : 0;
            default -> throw new RuntimeException("Unknown unary operator: " + m_operator);
        };
    }

    @Override
    public InstrIntf codegen(com.compiler.CompileEnvIntf compileEnv) {
        constFold();
        if (m_constValue != null) {
            InstrIntf constInstr = new InstrIntegerLiteral(m_constValue.toString());
            compileEnv.addInstr(constInstr);
            return constInstr;
        }
        InstrIntf operand = m_operand.codegen(compileEnv);
        InstrIntf unaryExpr = new InstrUnaryExpr(m_operator, operand);
        compileEnv.addInstr(unaryExpr);
        return unaryExpr;
    }

    @Override
    public Integer constFold() {
        Integer operandValue = m_operand.constFold();
        if (operandValue == null)
            return null;

        m_constValue = switch (m_operator) {
            case MINUS -> -operandValue;
            case NOT -> operandValue == 0 ? 1 : 0;
            default -> throw new RuntimeException("Unknown unary operator: " + m_operator);
        };
        return m_constValue;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + this + "\n");
    }
}
