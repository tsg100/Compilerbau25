package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrShift;

public class ASTShiftExprNode extends ASTExprNode{
	
	ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    com.compiler.TokenIntf.Type m_operator;

    public ASTShiftExprNode(ASTExprNode operand0, ASTExprNode operand1, com.compiler.TokenIntf.Type operator) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_operator = operator;
    }

    public int eval() {
       int operand0 = m_operand0.eval();
       int operand1 = m_operand1.eval();
       if (m_operator == com.compiler.TokenIntf.Type.SHIFTLEFT) {
          return operand0 << operand1;
       } else {
    	  return operand0 >> operand1;
       }
    }
    
    public InstrIntf codegen(CompileEnvIntf compileEnv) {
        InstrIntf operand0 = m_operand0.codegen(compileEnv);
        InstrIntf operand1 = m_operand1.codegen(compileEnv);
        InstrIntf shiftInstr = new InstrShift(m_operator, operand0, operand1);
        compileEnv.addInstr(shiftInstr);
        return shiftInstr;
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
    	outStream.write(indent);
        outStream.write("ShiftExpr ");
        outStream.write(m_operator.toString());
        outStream.write("\n");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }
}
