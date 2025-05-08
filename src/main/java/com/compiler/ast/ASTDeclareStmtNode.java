package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTDeclareStmtNode extends ASTStmtNode {

    String m_identifier;

    public ASTDeclareStmtNode(String ident) {
        m_identifier = ident;
    }

    @Override
    public void execute(OutputStreamWriter out) {

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent+ "DECLARE "+m_identifier+";");
    }
    
}
