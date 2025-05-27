package com.compiler.ast;

import com.compiler.CompileEnvIntf;

import java.io.OutputStreamWriter;

public class ASTCaseNode extends ASTStmtNode {

    ASTIntegerLiteralNode m_value;
    ASTStmtListNode m_stmtList;

    int expressionValue; // Value gets written on Execution of Switch Block

    public ASTCaseNode(ASTIntegerLiteralNode value, ASTStmtListNode stmtList) {
        m_stmtList = stmtList;
        m_value = value;

    }

    @Override
    public void execute(OutputStreamWriter out) {
        if(m_value.eval()==expressionValue) m_stmtList.execute(out);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTCaseNode\n");
        m_stmtList.print(outStream, indent + "  ");
    }
}
