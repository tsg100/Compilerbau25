package com.compiler.ast;

import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrJump;
import com.compiler.instr.InstrPopStack;
import com.compiler.instr.InstrPushCallStack;
import com.compiler.instr.InstrPushStack;

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


    /*
    * 1. Argumente auf Stack pushen
    * 2. Rücksprungadresse auf CallStack pushen
    * 3. JumpInstr zum Function Body
    * 4. Pop Instruction für Rückgabewert
    */
    @Override
    public InstrIntf codegen(com.compiler.CompileEnvIntf env) {

        // 1.
        m_argumentList.stream()
                .map(arg -> arg.codegen(env))
                .forEach(arg -> env.addInstr(new InstrPushStack(arg)));

        // 2.
        env.addInstr(new InstrPushCallStack());

        // 3.
        InstrBlock functionBody = env.getFunctionTable().getFunction(m_funcName).m_body;
        env.addInstr(new InstrJump(functionBody));

        // 4.
        InstrIntf popInstr = new InstrPopStack();
        env.addInstr(popInstr);
        return popInstr;
    }

}
