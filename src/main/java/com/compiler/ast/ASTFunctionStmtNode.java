package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.List;

import com.compiler.CompileEnvIntf;
import com.compiler.FunctionInfo;
import com.compiler.FunctionTable;
import com.compiler.InstrBlock;

public class ASTFunctionStmtNode extends ASTStmtNode {
    String m_functionName;
    List<String> m_parameterList;
    List<ASTStmtNode> m_functionBody;

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
    public void codegen(CompileEnvIntf env) {
        super.codegen(env);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {}
}
