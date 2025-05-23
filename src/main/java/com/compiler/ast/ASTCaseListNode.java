package com.compiler.ast;

import com.compiler.CompileEnvIntf;

import java.io.OutputStreamWriter;
import java.util.List;

public class ASTCaseListNode extends ASTStmtNode {

    List<ASTCaseNode> m_caseList;
    public ASTCaseListNode(List<ASTCaseNode> caseList) {
        m_caseList = caseList;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        m_caseList.forEach(caseItem->caseItem.execute(out));
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTCaseListNode\n");
        m_caseList.forEach(caseItem -> {
            try {
                caseItem.print(outStream, indent + "  ");
                outStream.write("\n");
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
