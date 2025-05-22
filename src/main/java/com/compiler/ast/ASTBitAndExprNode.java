package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf;
import com.compiler.instr.InstrBitAnd;
import com.compiler.instr.InstrIntegerLiteral;

import java.io.OutputStreamWriter;

public class ASTBitAndExprNode extends ASTExprNode {

    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    TokenIntf.Type m_operator;
    Integer m_constValue;

    public ASTBitAndExprNode(final ASTExprNode operand0, final ASTExprNode operand1, final TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
       final int operand0 = m_operand0.eval();
       final int operand1 = m_operand1.eval();
       return operand0 & operand1;
    }

    public void print(final OutputStreamWriter outStream, final String indent) throws Exception {
        outStream.write(indent);
        outStream.write("BitAndExpression ");
        outStream.write(m_operator.toString());
        outStream.write("\n");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }


    @Override
    public InstrIntf codegen(final CompileEnvIntf env) {
        constFold();
        if(m_constValue != null) {
            final InstrIntegerLiteral constInstr = new InstrIntegerLiteral(m_constValue.toString());
            env.addInstr(constInstr);
            return constInstr;
        }
        final InstrIntf operand0 = m_operand0.codegen(env);
        final InstrIntf operand1 = m_operand1.codegen(env);
        final InstrIntf bitAnd = new InstrBitAnd(m_operator, operand0, operand1);
        env.addInstr(bitAnd);
        return bitAnd;
    }


    @Override
    public Integer constFold() {
        final Integer lhsConst = m_operand0.constFold();
        final Integer rhsConst = m_operand1.constFold();

        if(lhsConst != null && rhsConst != null) {
            m_constValue = lhsConst & rhsConst;
        } else {
            m_constValue = null;
        }
        return m_constValue;
    }
}
