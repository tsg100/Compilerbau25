package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrJump;
import com.compiler.instr.InstrPushStack;
import com.compiler.instr.InstrReturn;

import java.io.OutputStreamWriter;

public class ASTReturnStmtNode extends ASTStmtNode {
    ASTExprNode m_expr;
    public ASTReturnStmtNode(ASTExprNode questionMarkExpr) {
        super();
        m_expr = questionMarkExpr;
    }

    @Override
    public void execute(OutputStreamWriter out) {

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
    @Override
    public void codegen(CompileEnvIntf env) {
        InstrIntf result = m_expr.codegen(env);
        env.addInstr(new InstrPushStack(result));
        env.addInstr(new InstrReturn());
    }
}
