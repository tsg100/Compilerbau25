package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrCondJump;
import com.compiler.instr.InstrJump;

public class ASTWhileLoopStmtNode extends ASTStmtNode {

    ASTExprNode m_predicate;

    ASTStmtNode m_loopBody;

    public ASTWhileLoopStmtNode(ASTExprNode condition, ASTStmtNode loopBody) {
        this.m_predicate = condition;
        this.m_loopBody = loopBody;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        while (m_predicate.eval() != 0) {
            m_loopBody.execute(out);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "WHILE\n");
        m_predicate.print(outStream, "  " + indent);
        m_loopBody.print(outStream, "  " + indent);
    }

    @Override
    public void codegen(CompileEnvIntf env) {
        InstrBlock whileHead = env.createBlock("WhileLoopHead");
        InstrBlock whileBody = env.createBlock("WhileLoopBody");
        InstrBlock exit = env.createBlock("WhileExitBlock");
        InstrJump jumpToHead = new InstrJump(whileHead);
        env.addInstr(jumpToHead);

        env.setCurrentBlock(whileBody);

        m_loopBody.codegen(env);

        env.addInstr(new InstrJump(whileHead));

        env.setCurrentBlock(whileHead);
        InstrCondJump conditionalJump = new InstrCondJump(m_predicate.codegen(env), whileBody, exit);

        whileHead.addInstr(conditionalJump);

        env.setCurrentBlock(exit);
    }
}
