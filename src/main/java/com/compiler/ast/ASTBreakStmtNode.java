package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.instr.InstrJump;
import java.io.OutputStreamWriter;

public class ASTBreakStmtNode extends ASTStmtNode{
    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "BREAK\n");
    }

    @Override
    public void execute(OutputStreamWriter out) {
    }

    @Override
    public void codegen(CompileEnvIntf env){
        InstrJump jumpToLoopExit = new InstrJump(env.popLoopStack());
        env.addInstr(jumpToLoopExit);
    }

}
