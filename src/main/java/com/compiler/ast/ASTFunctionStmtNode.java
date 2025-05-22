package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import com.compiler.CompileEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.instr.InstrAssign;
import com.compiler.instr.InstrPopStack;

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

    /*
    * 1. momentanen Block speichern
    * 2. Body Block der Function erstellen
    * 3. Current Environment Block auf Body Block setzen
    * 4. Assign Instructions der Variablen mit PopInstructions
    * 5. Codegenerierung des Function Bodys
    * 6. body Block in die FunctionTable eintragen
    * 7. Current Environment Block zu ursprünglichen Block zuweisen
    */
    @Override
    public void codegen(CompileEnvIntf env) {
        InstrBlock exitBlock = env.getCurrentBlock();

        InstrBlock bodyBlock = env.createBlock("function_"+m_functionName);
        env.setCurrentBlock(bodyBlock);
        Collections.reverse(m_parameterList);
        for (String parameter : m_parameterList) {
            InstrPopStack popInstr = new InstrPopStack();
            env.addInstr(popInstr);
            env.addInstr(new InstrAssign(env.getSymbolTable().getSymbol(parameter), popInstr));
        }
        m_functionBody.forEach(s -> s.codegen(env));

        env.getFunctionTable().getFunction(m_functionName).m_body = bodyBlock;

        env.setCurrentBlock(exitBlock);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {

    }
}
