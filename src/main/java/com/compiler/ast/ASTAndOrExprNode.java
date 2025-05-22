package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf.Type;
import com.compiler.instr.InstrAndOr;

import java.io.OutputStreamWriter;

public class ASTAndOrExprNode extends ASTExprNode {
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    Type m_operator;
    Integer m_constValue;

    public ASTAndOrExprNode(ASTExprNode operand0, ASTExprNode operand1, Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
        int operand0 = m_operand0.eval();
        int operand1 = m_operand1.eval();
        if (m_operator == Type.AND) {
            return operand0 == 0 || operand1 == 0 ? 0 : 1;
        } else {
            return operand0 == 0 && operand1 == 0 ? 0 : 1;
        }
    }

    public InstrIntf codegen(CompileEnvIntf compileEnv) {
        InstrIntf operand0 = m_operand0.codegen(compileEnv);
        InstrIntf operand1 = m_operand1.codegen(compileEnv);
        InstrIntf andOrInstr = new InstrAndOr(m_operator, operand0, operand1);
        compileEnv.addInstr(andOrInstr);
        return andOrInstr;
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTAndOrExprNode ");
        outStream.write(m_operator.toString());
        outStream.write("\n");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }

    public Integer constFold() {
        Integer lhsConst = m_operand0.constFold();
        Integer rhsConst = m_operand1.constFold();
        if (lhsConst != null && rhsConst != null) {
            if (m_operator == Type.AND) {
                m_constValue = lhsConst == 0 || rhsConst == 0 ? 0 : 1;
            } else {
                m_constValue = lhsConst == 0 && rhsConst == 0 ? 0 : 1;
            }

        } else {
            m_constValue = null;
        }
        return m_constValue;
    }
}
