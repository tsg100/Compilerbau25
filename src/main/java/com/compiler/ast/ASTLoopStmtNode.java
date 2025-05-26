package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrJump;

import java.io.OutputStreamWriter;

public class ASTLoopStmtNode extends ASTStmtNode{

    //loopstmt : loopstart stmtlist loopend
    //
    //loopstart : LOOP LBRACE
    //
    //loopend : RBRACE ENDLOOP

    private ASTStmtListNode m_body;

    public ASTLoopStmtNode(ASTStmtListNode body) {
        m_body = body;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        m_body.execute(out);
    }

    @Override
    public void codegen(CompileEnvIntf env){

        InstrBlock loopBody = env.createBlock("LoopBody");
        InstrBlock loopExit = env.createBlock("LoopExit");
        env.pushLoopStack(loopExit);
        InstrJump jumpToBody = new InstrJump(loopBody);
        env.addInstr(jumpToBody);

        env.setCurrentBlock(loopBody);
        m_body.codegen(env);
        env.addInstr(jumpToBody);

        env.setCurrentBlock(loopExit);


    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "LOOP\n");
        m_body.print(outStream, "  " + indent);
    }
}
