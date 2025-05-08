package com.compiler.ast;

import java.io.OutputStreamWriter;
import java.util.List;

public class ASTStmtListNode extends ASTStmtNode {

    final List<ASTStmtNode> stmts;


    public ASTStmtListNode(final List<ASTStmtNode> stmtList) {
        this.stmts = stmtList;
    }


    @Override
    public void execute(final OutputStreamWriter out) {
        this.stmts.forEach(stmt -> stmt.execute(out));
    }


    @Override
    public void print(final OutputStreamWriter outStream, final String indent) throws Exception {
        this.stmts.forEach(stmt -> {
            try {
                stmt.print(outStream, indent);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
