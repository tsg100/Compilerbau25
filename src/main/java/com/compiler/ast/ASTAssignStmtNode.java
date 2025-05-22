package com.compiler.ast;

import java.io.OutputStreamWriter;

import com.compiler.Symbol;
import com.compiler.Token;

public class ASTAssignStmtNode extends ASTStmtNode{

	private Symbol identifier;
	private ASTExprNode expr;
	
	
	
	public ASTAssignStmtNode(Symbol identifier, ASTExprNode expr) {
		super();
		this.identifier = identifier;
		this.expr = expr;
	}

	@Override
	public void execute(OutputStreamWriter out) {
		identifier.m_number = expr.eval();
	}

	@Override
	public void codegen(com.compiler.CompileEnvIntf env) {
		com.compiler.InstrIntf exprInstr = expr.codegen(env);
		com.compiler.InstrIntf assignInstr = new com.compiler.instr.InstrAssign(identifier, exprInstr);
		env.addInstr(assignInstr);
	}

	@Override
	public void print(OutputStreamWriter outStream, String indent) throws Exception {
		outStream.write(indent);
		outStream.write("ASTAssignStmtNode ");
		outStream.write(identifier.m_name);
		outStream.write("\n");
		expr.print(outStream, indent + "  ");
	}

}
