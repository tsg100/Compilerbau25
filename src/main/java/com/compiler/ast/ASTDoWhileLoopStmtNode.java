package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrCondJump;
import com.compiler.instr.InstrJump;

public class ASTDoWhileLoopStmtNode extends ASTStmtNode {

    ASTExprNode m_predicate;

    ASTStmtNode m_loopBody;

    public ASTDoWhileLoopStmtNode(ASTExprNode condition, ASTStmtNode loopBody) {
        this.m_predicate = condition;
        this.m_loopBody = loopBody;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        do {
            m_loopBody.execute(out);
        } while (m_predicate.eval() != 0);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "DO WHILE\n");
        m_predicate.print(outStream, "  " + indent);
        m_loopBody.print(outStream, "  " + indent);
    }

    @Override
    public void codegen(CompileEnvIntf env) {
        InstrBlock doWhile = env.createBlock("DoWhileBlock");
        InstrBlock exit = env.createBlock("DoWhileExitBlock");

        InstrJump jumpToLoop = new InstrJump(doWhile);
        env.addInstr(jumpToLoop);

        env.setCurrentBlock(doWhile);
        m_loopBody.codegen(env);
        InstrCondJump conditionalJump = new InstrCondJump(m_predicate.codegen(env), doWhile, exit);
        env.addInstr(conditionalJump);

        env.setCurrentBlock(exit);
    }
}
