package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrIntegerLiteral;
import com.compiler.instr.QuestionMarkInstr;

public class ASTQuestionMarkNode extends ASTExprNode {
    ASTExprNode m_predicate;
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;
    Integer m_constValue;

    public ASTQuestionMarkNode(ASTExprNode predicate, ASTExprNode operand0, ASTExprNode operand1) {
        m_operand0 = operand0;
        m_operand1 = operand1;
        m_predicate = predicate;
    }
    @Override
    public int eval() {
        return (m_predicate.eval() != 0) ? m_operand0.eval() : m_operand1.eval();
    }

    @Override
    public Integer constFold() {
        Integer constPred = m_predicate.constFold();
        Integer constOperand0 = m_operand0.constFold();
        Integer constOperand1 = m_operand1.constFold();
        if(constPred == null){
            m_constValue = null;
        } else if (constPred != 0 && constOperand0 != null){
            m_constValue = constOperand0;
        } else if (constPred == 0 && constOperand1 != null){
            m_constValue = constOperand1;
        }
        return m_constValue;
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        constFold();
        if(m_constValue != null){
            InstrIntf constInstr = new InstrIntegerLiteral(m_constValue.toString());
            env.addInstr(constInstr);
            return constInstr;
        }

        InstrIntf predicateInstr = m_predicate.codegen(env);
        InstrIntf op0Instr = m_operand0.codegen(env);
        InstrIntf op1Instr = m_operand1.codegen(env);
        InstrIntf questionMarkInstr = new QuestionMarkInstr(predicateInstr, op0Instr, op1Instr);
        env.addInstr(questionMarkInstr);
        return questionMarkInstr;
    }

    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("QuestionMarkExpr ");
        outStream.write("\n");
        m_predicate.print(outStream, indent + "  ");
        m_operand0.print(outStream, indent + "  ");
        m_operand1.print(outStream, indent + "  ");
    }
}
