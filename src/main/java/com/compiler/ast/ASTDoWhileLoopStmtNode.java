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
        InstrBlock whileHead = env.createBlock("DoWhileLoopHead");
        InstrBlock whileBody = env.createBlock("DoWhileLoopBody");
        InstrBlock exit = env.createBlock("DoWhileExitBlock");
        InstrJump jumpToBody = new InstrJump(whileBody);
        env.addInstr(jumpToBody);

        env.setCurrentBlock(whileBody);
        m_loopBody.codegen(env);
        whileBody.addInstr(new InstrJump(whileHead));

        env.setCurrentBlock(whileHead);
        InstrCondJump conditionalJump = new InstrCondJump(m_predicate.codegen(env), whileBody, exit);

        whileHead.addInstr(conditionalJump);

        env.setCurrentBlock(exit);
    }
}
