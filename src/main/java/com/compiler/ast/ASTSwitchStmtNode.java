package com.compiler.ast;

import com.compiler.*;
import com.compiler.instr.*;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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
    public void codegen(CompileEnvIntf env) {
        InstrBlock headBlock = env.createBlock(env.createUniqueSymbol("Switch_Head").m_name);
        env.addInstr(new InstrJump(headBlock));
        env.setCurrentBlock(headBlock);
        InstrBlock exitBlock = env.createBlock(env.createUniqueSymbol("Switch_Exit").m_name);

        if(m_caseList.m_caseList.isEmpty()){
            env.addInstr(new InstrJump(exitBlock));
            env.setCurrentBlock(exitBlock);
            return;
        }

        InstrIntf switchExpression = m_expression.codegen(env);
        Symbol switchExpressionSymbol = env.createUniqueSymbol("switch");
        InstrIntf assignSwitchExpression = new InstrAssign(switchExpressionSymbol, switchExpression);
        env.addInstr(assignSwitchExpression);

        //Body Block Loop
        List<InstrBlock> bodyBlocks = new ArrayList<>();
        for (int i = 0; i < m_caseList.m_caseList.size(); i++) {
            InstrBlock bodyBlock = env.createBlock(env.createUniqueSymbol("CaseExecute" + i).m_name);
            bodyBlocks.add(bodyBlock);
            env.setCurrentBlock(bodyBlock);
            m_caseList.m_caseList.get(i).m_stmtList.codegen(env);
            env.addInstr(new InstrJump(exitBlock));
        }

        //Check Block Loop
        List<InstrBlock> checkBlocks = new ArrayList<>();
        List<InstrIntf> equalInstrs =  new ArrayList<>();
        for (int i = 0; i < m_caseList.m_caseList.size(); i++) {
            InstrBlock caseBlock = env.createBlock(env.createUniqueSymbol("CaseCheck" + i).m_name);
            checkBlocks.add(caseBlock);
            env.setCurrentBlock(caseBlock);
            InstrIntf switchValue = new InstrVariableExpr(switchExpressionSymbol);
            env.addInstr(switchValue);
            InstrIntf caseLiteral = m_caseList.m_caseList.get(i).m_value.codegen(env);
            InstrIntf equals = new InstrCompare(TokenIntf.Type.EQUAL, switchValue, caseLiteral);
            equalInstrs.add(equals);
            env.addInstr(equals);
        }

        for (int i = 0; i < checkBlocks.size() - 1; i++) {
            env.setCurrentBlock(checkBlocks.get(i));
            env.addInstr(new InstrCondJump(equalInstrs.get(i), bodyBlocks.get(i), checkBlocks.get(i+1)));
        }

        env.setCurrentBlock(checkBlocks.get(checkBlocks.size()-1));
        env.addInstr(new InstrCondJump(equalInstrs.get(checkBlocks.size()-1), bodyBlocks.get(checkBlocks.size()-1), exitBlock));

        env.setCurrentBlock(headBlock);
        env.addInstr(new InstrJump(checkBlocks.get(0)));

        env.setCurrentBlock(exitBlock);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("ASTSwitchStmtNode\n");
        m_expression.print(outStream, indent + "  ");
        m_caseList.print(outStream, indent + "  ");
    }
}
