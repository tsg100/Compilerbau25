package com.compiler.ast;

public abstract class ASTExprNode extends ASTNode {
    public abstract int eval();
    public com.compiler.InstrIntf codegen(com.compiler.CompileEnvIntf env) {
        return null;
    }
    public Integer constFold() {
        return null;
    }
}
