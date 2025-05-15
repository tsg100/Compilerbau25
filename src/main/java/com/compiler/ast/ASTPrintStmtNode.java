package com.compiler.ast;


import java.io.IOException;
import java.io.OutputStreamWriter;

public class ASTPrintStmtNode extends ASTStmtNode{

    private final ASTExprNode astExprNode;

    public ASTPrintStmtNode(ASTExprNode astExprNode) {
        this.astExprNode = astExprNode;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        String result = Integer.toString(astExprNode.eval());
        try {
            out.write(result + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTPrintStmtNode ");
        outStream.write("\n");
        astExprNode.print(outStream, indent + "  ");
    }

    @Override
    public void codegen(com.compiler.CompileEnvIntf env) {
        com.compiler.InstrIntf expr = this.astExprNode.codegen(env);
        com.compiler.InstrIntf printInstr = new com.compiler.instr.InstrPrint(expr);
        env.addInstr(printInstr); 
    }  
}
