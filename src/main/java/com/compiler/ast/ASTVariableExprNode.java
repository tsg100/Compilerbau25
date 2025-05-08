package com.compiler.ast;

import com.compiler.Symbol;
import java.io.OutputStreamWriter;

public class ASTVariableExprNode extends ASTExprNode {

    private final Symbol m_symbol;

    public ASTVariableExprNode(Symbol m_symbol) {
        this.m_symbol = m_symbol;
    }

    @Override
    public int eval() {
        return m_symbol.m_number;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception { }
}
