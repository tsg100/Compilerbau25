package com.compiler.ast;


import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf;
import com.compiler.instr.InstrCompare;
import com.compiler.instr.InstrCondJump;
import com.compiler.instr.InstrIntegerLiteral;
import com.compiler.instr.InstrJump;

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
        final InstrBlock negativeBlock = env.createBlock("numericIf_negativeBlock");
        final InstrBlock positiveBlock = env.createBlock("numericIf_positiveBlock");
        final InstrBlock zeroBlock = env.createBlock("numericIf_zeroBlock");
        final InstrBlock checkPositivBlock = env.createBlock("numericIf_checkPositivBlock");
        final InstrBlock exitBlock = env.createBlock("numericIf_exitBlock");

        // Evaluate predicate
        final InstrIntf result = predicate.codegen(env);

        // Instruction to jump in negativblock or check positiv block
        final InstrIntf compareLessThan0 = new InstrCompare(TokenIntf.Type.LESS, result, new InstrIntegerLiteral("0"));
        final InstrIntf conJumpWhenNegativ = new InstrCondJump(compareLessThan0, negativeBlock, checkPositivBlock);
        env.addInstr(compareLessThan0);
        env.addInstr(conJumpWhenNegativ);

        // Check positiv block
        env.setCurrentBlock(checkPositivBlock);
        final InstrIntf result2 = predicate.codegen(env);
        final InstrIntf compareGreaterThan0 = new InstrCompare(TokenIntf.Type.GREATER, result2, new InstrIntegerLiteral("0"));
        final InstrIntf conJumpWhenPositiv = new InstrCondJump(compareGreaterThan0, positiveBlock, zeroBlock);
        env.addInstr(compareGreaterThan0);
        env.addInstr(conJumpWhenPositiv);

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
