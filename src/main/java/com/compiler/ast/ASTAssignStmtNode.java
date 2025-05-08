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
	public void print(OutputStreamWriter outStream, String indent) throws Exception {}

}
