package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.Symbol;
import com.compiler.TokenIntf.Type;
import com.compiler.instr.InstrAssign;
import com.compiler.instr.InstrCompare;
import com.compiler.instr.InstrCondJump;
import com.compiler.instr.InstrIntegerLiteral;
import com.compiler.instr.InstrJump;
import com.compiler.instr.InstrPlusMinus;
import com.compiler.instr.InstrVariableExpr;

public class ASTExecuteNTimesNode extends ASTStmtNode{

	private final ASTStmtListNode bodyNode;
	private final ASTExprNode count;
	
	public ASTExecuteNTimesNode(ASTExprNode count, ASTStmtListNode bodyNode) {
		this.bodyNode = bodyNode;
		this.count = count;
	}

	@Override
	public void execute(OutputStreamWriter out) {
		executeTimes(out, count.eval());
	}
	
	private void executeTimes(OutputStreamWriter out, int remaining){
	    if (remaining <= 0) {
	        return;
	    }
	    bodyNode.execute(out);
	    
	    executeTimes(out, remaining - 1);
	}

	@Override
	public void print(OutputStreamWriter outStream, String indent) throws Exception {

		outStream.write(indent);
        outStream.write("ASTExecuteNTimesNode ");
        outStream.write("\n");
        count.print(outStream, indent + "  ");
        bodyNode.print(outStream, indent + "  ");
		
	}
	

    @Override
    public void codegen(CompileEnvIntf env) {
        // create code blocks needed for control structure
    	InstrBlock headBlock = env.createBlock("executeTimes_head");
        InstrBlock bodyBlock = env.createBlock("executeTimes_body");
        InstrBlock exitBlock = env.createBlock("executeTimes_exit");

        InstrIntf countInstr =  count.codegen(env);
        Symbol countSymbol = env.createUniqueSymbol("counter");
        InstrIntf assignCounter = new InstrAssign(countSymbol, countInstr);
        env.addInstr(assignCounter);
        
        
        Symbol iterCounterSymbol = env.createUniqueSymbol("tmp");
        InstrIntegerLiteral zeroLiteral = new InstrIntegerLiteral("0");
        env.addInstr(zeroLiteral);
		InstrIntf remainingIter = new InstrAssign(iterCounterSymbol, zeroLiteral);
		env.addInstr(remainingIter);
       
        InstrIntf entry2headJump = new InstrJump(headBlock);
        env.addInstr(entry2headJump);

        
        
        //Execute head block
        env.setCurrentBlock(headBlock);
        
        InstrIntf loadRemaining1 = new InstrVariableExpr(iterCounterSymbol);
        env.addInstr(loadRemaining1);
        
        InstrIntf loadCounter = new InstrVariableExpr(countSymbol);
        env.addInstr(loadCounter);
        
        InstrIntf compareIntf = new InstrCompare(Type.LESS, loadRemaining1, loadCounter);
        env.addInstr(compareIntf);
        
        InstrIntf condjmp = new InstrCondJump(compareIntf, bodyBlock, exitBlock);
        env.addInstr(condjmp);
        
        
        
        
        //Execute body 
        env.setCurrentBlock(bodyBlock);
        
        bodyNode.codegen(env);

        InstrIntf loadRemaining2 = new InstrVariableExpr(iterCounterSymbol);
        env.addInstr(loadRemaining2);
        
        InstrIntegerLiteral oneLiteral = new InstrIntegerLiteral("1");
        env.addInstr(oneLiteral);
		InstrIntf remainingIterIncrement= new InstrPlusMinus(Type.PLUS, loadRemaining2, oneLiteral);
		env.addInstr(remainingIterIncrement);
        
        InstrIntf assignIncrementedIter = new InstrAssign(iterCounterSymbol, remainingIterIncrement);
        env.addInstr(assignIncrementedIter);
        
        

        InstrIntf jumpBackToHead = new InstrJump(headBlock);
        env.addInstr(jumpBackToHead);
        
        
        
        
        // Exit Block
        env.setCurrentBlock(exitBlock);
    }

}
