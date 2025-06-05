package com;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class ExprEvalMain {
public static void main(String[] args) throws Exception {
		// create input stream
		CharStream input = CharStreams.fromString("4 + 5 - 2");
		// create lexer
		com.compiler.antlr.languageLexer lexer = new com.compiler.antlr.languageLexer(input);
		// create token stream
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// create parser
		com.compiler.antlr.languageParser parser = new com.compiler.antlr.languageParser(tokens);
		parser.setBuildParseTree(true);
		// build parse tree
		ParseTree tree = parser.expr();

		// output parse tree
		System.out.println(tree.toStringTree(parser));


		// create visitor for expression evaluation
		com.compiler.antlrcompiler.ExprEval  exprEvalVisitor = new com.compiler.antlrcompiler.ExprEval();
		Integer result = exprEvalVisitor.visit(tree);
		System.out.println(result);
}


	}