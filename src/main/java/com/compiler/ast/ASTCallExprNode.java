package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.List;

public class ASTCallExprNode extends ASTExprNode {
    private final String m_funcName;
    private final List<ASTExprNode> m_argumentList;
    public ASTCallExprNode(String funcName, List<ASTExprNode> argumentList) {
        super();
        m_funcName = funcName;
        m_argumentList = argumentList;
    }

    @Override
    public int eval() {
        return 0;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
}
