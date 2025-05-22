package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.TokenIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class ASTTDashNode extends ASTExprNode {
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator = TokenIntf.Type.TDASH;
    Integer m_constValue;

    public ASTTDashNode(ASTExprNode operand0, ASTExprNode operand1) {
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    public int eval() {
       int operand0 = m_operand0.eval();
       int operand1 = m_operand1.eval();
      return (int) Math.pow(operand0,operand1);
    }

    public InstrIntf codegen(CompileEnvIntf compileEnv) {
        constFold();
        if (m_constValue != null) {
            com.compiler.InstrIntf constInstr = new com.compiler.instr.InstrIntegerLiteral(m_constValue.toString());
            compileEnv.addInstr(constInstr);
            return constInstr;
        }

        InstrIntf operand0 = m_operand0.codegen(compileEnv);
        InstrIntf operand1 = m_operand1.codegen(compileEnv);
        InstrIntf instrTDash = new com.compiler.instr.InstrTDash(m_operator, operand0, operand1);
        compileEnv.addInstr(instrTDash);
        return instrTDash;
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("DashExpr ");
        outStream.write(m_operator.toString());
        outStream.write("\n");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");

    }
    public Integer constFold() {
        Integer lhsConst = m_operand0.constFold();
        Integer rhsConst = m_operand1.constFold();
        if (lhsConst != null && rhsConst != null) {
            m_constValue = (int) Math.pow(lhsConst, rhsConst);
        } else {
            m_constValue = null;
        }
        return m_constValue;
    }
}
