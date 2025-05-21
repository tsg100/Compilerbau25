package com.compiler.ast;


import com.compiler.InstrBlock;
import com.compiler.instr.InstrJump;
import com.compiler.instr.InstrJumpIfNegative;
import com.compiler.instr.InstrJumpIfPositive;

import java.io.OutputStreamWriter;

public class ASTNumericIfNode extends ASTStmtNode {

    private final ASTExprNode predicate;
    private final ASTStmtListNode positiveStmtList;
    private final ASTStmtListNode negativeStmtList;
    private final ASTStmtListNode zeroStmtList;


    public ASTNumericIfNode(final ASTExprNode predicate, final ASTStmtListNode positiveStmtList, final ASTStmtListNode negativeStmtList, final ASTStmtListNode zeroStmtList) {
        this.predicate = predicate;
        this.positiveStmtList = positiveStmtList;
        this.negativeStmtList = negativeStmtList;
        this.zeroStmtList = zeroStmtList;
    }


    @Override
    public void execute(final OutputStreamWriter out) {
        final int result = predicate.eval();

        if(result == 0) {
            zeroStmtList.execute(out);
        } else if(result > 0) {
            positiveStmtList.execute(out);
        } else {
            negativeStmtList.execute(out);
        }
    }


    @Override
    public void print(final OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTNumericIfNode ");
        indent += "  ";
        outStream.write("\n");
        outStream.write(indent + "positiveBlock");
        outStream.write("\n");
        positiveStmtList.print(outStream, indent + "  ");
        outStream.write("\n");
        outStream.write(indent + "negativeBlock");
        outStream.write("\n");
        negativeStmtList.print(outStream, indent + "  ");
        outStream.write("\n");
        outStream.write(indent + "zeroBlock");
        outStream.write("\n");
        zeroStmtList.print(outStream, indent + "  ");
    }


    @Override
    public void codegen(final com.compiler.CompileEnvIntf env) {
        final int result = predicate.eval();

        final InstrBlock negativeBlock = env.createBlock("numericIf_negativeBlock");
        final InstrBlock positiveBlock = env.createBlock("numericIf_positiveBlock");
        final InstrBlock zeroBlock = env.createBlock("numericIf_zeroBlock");
        final InstrBlock exitBlock = env.createBlock("numericIf_exitBlock");

        // Evaluate predicate
        predicate.codegen(env);

        final com.compiler.InstrIntf jumpIfNegative = new InstrJumpIfNegative(result, negativeBlock);
        final com.compiler.InstrIntf jumpIfPositive = new InstrJumpIfPositive(result, positiveBlock);
        final com.compiler.InstrIntf jumpToZero = new InstrJump(zeroBlock);
        env.addInstr(jumpIfNegative);
        env.addInstr(jumpIfPositive);
        env.addInstr(jumpToZero);

        // Negative block
        env.setCurrentBlock(negativeBlock);
        negativeStmtList.codegen(env);
        env.addInstr(new InstrJump(exitBlock));

        // Positive block
        env.setCurrentBlock(positiveBlock);
        positiveStmtList.codegen(env);
        env.addInstr(new InstrJump(exitBlock));

        // Zero block
        env.setCurrentBlock(zeroBlock);
        zeroStmtList.codegen(env);
        env.addInstr(new InstrJump(exitBlock));

        env.setCurrentBlock(exitBlock);
    }
}
