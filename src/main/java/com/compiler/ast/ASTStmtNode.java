package com.compiler.ast;

public abstract class ASTStmtNode extends ASTNode {
    public abstract void execute(java.io.OutputStreamWriter out);
}
