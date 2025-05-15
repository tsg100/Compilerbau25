package com.compiler.ast;


import java.io.IOException;
import java.io.OutputStreamWriter;

public class ASTJumpBlockNode extends ASTStmtNode{

    private final ASTStmtListNode bodyNode;

    public ASTJumpBlockNode(ASTStmtListNode bodyNode) {
        this.bodyNode = bodyNode;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        bodyNode.execute(out);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTJumpBlockNode ");
        outStream.write("\n");
        bodyNode.print(outStream, indent + "  ");
    }

    @Override
    public void codegen(com.compiler.CompileEnvIntf env) {
        // create code blocks needed for control structure
        com.compiler.InstrBlock bodyBlock = env.createBlock("jumpBlock_body");
        com.compiler.InstrBlock exitBlock = env.createBlock("jumpBlock_exit");

        // current block of CompileEnv is our entry block
        // terminate entry block with jump/conditional jump
        // into block of control structure
        com.compiler.InstrIntf entry2bodyJump = new com.compiler.instr.InstrJump(bodyBlock);
        env.addInstr(entry2bodyJump);

        // for each block of control structure (bodyBlock)
        // switch CompileEnv to the corresponding block
        env.setCurrentBlock(bodyBlock);
        // trigger codegen of statements that
        // belong into this block
        bodyNode.codegen(env);
        // terminate current block with jump
        com.compiler.InstrIntf body2exitJump = new com.compiler.instr.InstrJump(exitBlock);
        env.addInstr(body2exitJump);

        // switch CompileEnv to exit block
        env.setCurrentBlock(exitBlock);
    }
}
