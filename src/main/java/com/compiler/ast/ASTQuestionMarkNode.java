package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.instr.QuestionMarkInstr;

public class ASTQuestionMarkNode extends ASTExprNode {
    ASTExprNode m_predicate;
    ASTExprNode m_operand0;
    ASTExprNode m_operand1;

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
    public InstrIntf codegen(CompileEnvIntf env) {
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
