package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.instr.InstrPushValueStack;
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
        // nothing to do
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTReturnStmtNode ");
        outStream.write("\n");
        m_expr.print(outStream, indent + "  ");
    }
    @Override
    public void codegen(CompileEnvIntf env) {
        env.addInstr(new InstrPushValueStack(m_expr.codegen(env)));
        env.addInstr(new InstrReturn());
    }
}
