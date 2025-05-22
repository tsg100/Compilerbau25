package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf;

import com.compiler.instr.InstrCompare;
import com.compiler.instr.InstrIntegerLiteral;
import java.io.OutputStreamWriter;

public class ASTCompareExprNode extends ASTExprNode{

    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    TokenIntf.Type m_operator;
    Integer m_constValue;

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

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        constFold();

        if (m_constValue != null) {
            InstrIntf constInstr = new InstrIntegerLiteral(m_constValue.toString());
            env.addInstr(constInstr);
            return constInstr;
        }

        final InstrIntf operand0 = m_operand0.codegen(env);
        final InstrIntf operand1 = m_operand1.codegen(env);
        final InstrIntf compareExp = new InstrCompare(m_operator, operand0, operand1);

        env.addInstr(compareExp);
        return compareExp;
    }

    @Override
    public Integer constFold() {
        Integer lhsConst = m_operand0.constFold();
        Integer rhsConst = m_operand1.constFold();

        if(lhsConst != null && rhsConst != null) {
            m_constValue = switch (m_operator) {
                case GREATER -> lhsConst > rhsConst ? 1 : 0;
                case LESS -> lhsConst < rhsConst ? 1 : 0;
                case EQUAL -> lhsConst == rhsConst ? 1 : 0;
                default -> null;
            };
        } else {
            m_constValue = null;
        }
        return m_constValue;
    }

}
