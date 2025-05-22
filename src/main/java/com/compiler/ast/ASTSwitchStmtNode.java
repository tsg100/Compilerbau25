package com.compiler.ast;

import java.io.OutputStreamWriter;

public class ASTSwitchStmtNode extends ASTStmtNode {

    ASTExprNode m_expression;
    ASTCaseListNode m_caseList;

    int evaluatedExpression;

    public ASTSwitchStmtNode(ASTExprNode expression, ASTCaseListNode caseList) {
        m_expression = expression;
        m_caseList = caseList;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        evaluatedExpression = m_expression.eval();
        m_caseList.m_caseList.forEach(caseItem-> caseItem.expressionValue = evaluatedExpression);
        m_caseList.execute(out);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTSwitchStmtNode\n");
        m_expression.print(outStream, indent + "  ");
        m_caseList.print(outStream, indent + "  ");
    }
}
