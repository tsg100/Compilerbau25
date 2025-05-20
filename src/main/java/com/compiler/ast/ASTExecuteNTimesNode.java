package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrAssign;
import com.compiler.instr.InstrIntegerLiteral;
import com.compiler.instr.InstrJump;

public class ASTExecuteNTimesNode extends ASTStmtNode{

	private final ASTStmtListNode bodyNode;
	private final int count;
	
	public ASTExecuteNTimesNode(int count, ASTStmtListNode bodyNode) {
		this.bodyNode = bodyNode;
		this.count = count;
	}

	@Override
	public void execute(OutputStreamWriter out) {
		executeTimes(out, count);
	}
	
	private void executeTimes(OutputStreamWriter out, int remaining){
	    if (remaining < 1) {
	        return;
	    }
	    execute(out);
	    executeTimes(out, remaining - 1);
	}

	@Override
	public void print(OutputStreamWriter outStream, String indent) throws Exception {

		outStream.write(indent);
        outStream.write("ASTExecuteNTimesNode ");
        outStream.write(count);
        outStream.write("\n");
        bodyNode.print(outStream, indent + "  ");
		
	}
	

    @Override
    public void codegen(CompileEnvIntf env) {
        // create code blocks needed for control structure
    	InstrBlock headBlock = env.createBlock("executeTimes_body");
        InstrBlock bodyBlock = env.createBlock("executeTimes_body");
        InstrBlock exitBlock = env.createBlock("executeTimes_exit");

        InstrIntf counter = new InstrIntegerLiteral(Integer.toString(count));
        env.addInstr(counter);
        
        InstrIntf remainingIter = new InstrIntegerLiteral(Integer.toString(0));
        env.addInstr(remainingIter);
        
       
        InstrIntf entry2headJump = new InstrJump(headBlock);
        env.addInstr(entry2headJump);

        // for each block of control structure (bodyBlock)
        // switch CompileEnv to the corresponding block
        env.setCurrentBlock(bodyBlock);
        // trigger codegen of statements that
        // belong into this block
        bodyNode.codegen(env);
        // terminate current block with jump
        InstrIntf body2exitJump = new InstrJump(exitBlock);
        env.addInstr(body2exitJump);

        // switch CompileEnv to exit block
        env.setCurrentBlock(exitBlock);
    }

}
