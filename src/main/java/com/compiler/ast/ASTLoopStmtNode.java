package com.compiler.ast;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrJump;

import java.io.OutputStreamWriter;

public class ASTLoopStmtNode extends ASTStmtNode{

    //loopstmt : loopstart stmtlist loopend
    //
    //loopstart : LOOP LBRACE
    //
    //loopend : RBRACE ENDLOOP

    private ASTStmtListNode m_body;

    public ASTLoopStmtNode(ASTStmtListNode body) {
        m_body = body;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        for(ASTStmtNode node : m_body.stmts){
            if((node instanceof ASTBreakExprNode)){
                node.execute(out);
            }
        }
    }

    @Override
    public void codegen(CompileEnvIntf env){



    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
}
