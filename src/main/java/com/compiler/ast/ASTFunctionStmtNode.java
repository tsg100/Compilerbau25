package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrAssign;
import com.compiler.instr.InstrPopValueStack;

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
        // nothing to do at runtime
    }

    @Override
    public void codegen(CompileEnvIntf env) {
        InstrBlock codegenEntryBlock = env.getCurrentBlock();

        InstrBlock functionBodyBlock = env.createBlock("function_"+m_functionName);
        env.setCurrentBlock(functionBodyBlock);

        m_parameterList.stream()
            .sorted(Collections.reverseOrder())
            .map(env.getSymbolTable()::getSymbol)
            .forEach(parameter -> {
                InstrPopValueStack popStackInstr = new InstrPopValueStack();
                env.addInstr(popStackInstr);
                InstrAssign assignInstr = new InstrAssign(parameter, popStackInstr);
                env.addInstr(assignInstr);
            });
        
        m_functionBody.forEach(stmt -> stmt.codegen(env));
        
        env.getFunctionTable().getFunction(m_functionName).m_body = functionBodyBlock;
        
        env.setCurrentBlock(codegenEntryBlock);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTFunctionStmtNode ");
        outStream.write("\n");
        for (ASTStmtNode astStmtNode : m_functionBody) {
            astStmtNode.print(outStream, indent + "  ");
        }    
    }
}
