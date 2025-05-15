package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.Token;
import com.compiler.TokenIntf;
import com.compiler.instr.InstrBitAnd;

import java.io.OutputStreamWriter;

public class ASTBitAndExprNode extends ASTExprNode {

    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    TokenIntf.Type m_operator;

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
        final InstrIntf operand0 = m_operand0.codegen(env);
        final InstrIntf operand1 = m_operand1.codegen(env);
        final InstrIntf bitAnd = new InstrBitAnd(m_operator, operand0, operand1);
        env.addInstr(bitAnd);
        return bitAnd;
    }
}
