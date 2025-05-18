package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.List;

public class ASTFunctionStmtNode extends ASTStmtNode {
    private final String m_functionName;
    private final List<String> m_parameterList;
    private final List<ASTStmtNode> m_functionBody;

    public ASTFunctionStmtNode(String functionName, List<String> parameterList, List<ASTStmtNode> functionBody) {
        super();
        m_functionName = functionName;
        m_parameterList = parameterList;
        m_functionBody = functionBody;
    }

    @Override
    public void execute(OutputStreamWriter out) {

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
}
