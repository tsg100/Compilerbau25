package com.compiler.ast;

import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.instr.*;

import java.io.OutputStreamWriter;

public class ASTIfElseNode extends ASTStmtNode{
    private ASTExprNode condition;
    private ASTStmtListNode trueStmtList;
    private ASTStmtListNode falseStmtList;

    public ASTIfElseNode(ASTExprNode condition, ASTStmtListNode trueStmtList, ASTStmtListNode elseStmtList) {
        this.condition = condition;
        this.trueStmtList = trueStmtList;
        this.falseStmtList = elseStmtList;
    }
    public ASTIfElseNode(ASTExprNode condition, ASTStmtListNode trueStmtList) {
        this.condition = condition;
        this.trueStmtList = trueStmtList;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        if(condition.eval() != 0){
            trueStmtList.execute(out);
        }
        else if(falseStmtList != null){
            falseStmtList.execute(out);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTIfElseNode");
        outStream.write("IF\n");
        condition.print(outStream, indent + "  ");
        outStream.write("THEN\n");
        trueStmtList.print(outStream, indent + "  ");
        if(falseStmtList != null){
            outStream.write("ELSE\n");
            falseStmtList.print(outStream, indent + "  ");
        }
    }

    @Override
    public void codegen(final com.compiler.CompileEnvIntf env) {
        InstrBlock trueBlock = env.createBlock("ifElse_trueBlock");
        InstrBlock exitBlock = env.createBlock("ifElse_exitBlock");

        InstrIntf jump;
        if(falseStmtList != null){
            InstrBlock falseBlock = env.createBlock("ifElse_falseBlock");
            jump = new InstrCondJump(condition.codegen(env), trueBlock, falseBlock);

            env.addInstr(jump);

            env.setCurrentBlock(falseBlock);
            falseStmtList.codegen(env);
            env.addInstr(new InstrJump(exitBlock));

        }
        else {
            jump = new InstrCondJump(condition.codegen(env), trueBlock, exitBlock);
            env.addInstr(jump);
        }

        env.setCurrentBlock(trueBlock);
        trueStmtList.codegen(env);
        env.addInstr(new InstrJump(exitBlock));

        env.setCurrentBlock(exitBlock);
    }
}
