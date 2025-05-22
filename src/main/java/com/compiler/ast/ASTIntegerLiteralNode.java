package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTIntegerLiteralNode extends ASTExprNode {
    public String m_value;
    
    public ASTIntegerLiteralNode(String value) {
        m_value = value;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("INTEGER ");
        outStream.write(m_value);
        outStream.write("\n");
    }

    @Override
    public int eval() {
        return Integer.valueOf(m_value);
    }

    @Override
    public com.compiler.InstrIntf codegen(com.compiler.CompileEnvIntf compileEnv) {
        com.compiler.InstrIntf literalInstr = new com.compiler.instr.InstrIntegerLiteral(m_value);
        compileEnv.addInstr(literalInstr);
        return literalInstr;
    }

    @Override
    public Integer constFold() {
        return Integer.valueOf(m_value);
    }
}
