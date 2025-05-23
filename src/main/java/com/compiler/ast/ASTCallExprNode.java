package com.compiler.ast;

import com.compiler.InstrBlock;
import com.compiler.InstrIntf;
import com.compiler.instr.InstrJump;
import com.compiler.instr.InstrPopValueStack;
import com.compiler.instr.InstrPushCallStack;
import com.compiler.instr.InstrPushValueStack;

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
        return 0; // not implemented, as Call Expression only returns a value with prior function stmt
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTCallExpressionNode ");
        outStream.write("\n");
        for (ASTExprNode astExprNode : m_argumentList) {
            astExprNode.print(outStream, indent + "  ");
        }
    }

    @Override
    public InstrIntf codegen(com.compiler.CompileEnvIntf env) {
        m_argumentList.stream()
                .map(arg -> arg.codegen(env))
                .forEach(argInstr -> env.addInstr(new InstrPushValueStack(argInstr)));

        env.addInstr(new InstrPushCallStack());

        InstrBlock functionBodyBlock = env.getFunctionTable().getFunction(m_funcName).m_body;
        env.addInstr(new InstrJump(functionBodyBlock));

        InstrIntf popInstr = new InstrPopValueStack();
        env.addInstr(popInstr);
        return popInstr;
    }

}
