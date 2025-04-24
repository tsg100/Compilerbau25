package com.compiler;

import com.compiler.TokenIntf.Type;

public class ExpressionEvaluator implements ExpressionEvaluatorIntf {
    private Lexer m_lexer;

    public ExpressionEvaluator(Lexer lexer) {
        m_lexer = lexer;
    }

    @Override
    public int eval(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    int getParantheseExpr() throws Exception {
        Token curToken = m_lexer.lookAhead();
        m_lexer.expect(Token.Type.INTEGER);
        return Integer.valueOf(curToken.m_value);
    }

    int getDashExpr() throws Exception {
        return getParantheseExpr();
    }
    
    int getUnaryExpr() throws Exception {
        return getDashExpr();
    }

    int getMulDivExpr() throws Exception {
        return getUnaryExpr();
    }
   
    int getPlusMinusExpr() throws Exception {
        // sumExpr: mulExpr (sumOp mulExpr)*
        int result = getMulDivExpr();
        while (
            m_lexer.lookAhead().m_type == TokenIntf.Type.PLUS ||
            m_lexer.lookAhead().m_type == TokenIntf.Type.MINUS
        ) {
            TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
            m_lexer.advance(); // getSumOp()
            int op = getMulDivExpr();
            if (tokenType == TokenIntf.Type.PLUS) {
                result += op;
            } else {
                result -= op;
            }
        }
        return result;
    }

    int getBitAndOrExpr() throws Exception {
        return getPlusMinusExpr();
    }

    int getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    int getCompareExpr() throws Exception {
        // compareExp: shiftExp (compOp shiftExp)*
        int input = getShiftExpr();


        while (
                m_lexer.lookAhead().m_type == Type.EQUAL ||
                m_lexer.lookAhead().m_type == Type.GREATER ||
                m_lexer.lookAhead().m_type == Type.LESS
        ) {
            TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
            m_lexer.advance();
            switch (tokenType){
                case GREATER:
                    if(input > getShiftExpr()){
                        input = 1;
                    }else {
                        input = 0;
                    }
                    break;
                case LESS:
                    if(input < getShiftExpr()){
                        input = 1;
                    }else {
                        input = 0;
                    }
                    break;
                case EQUAL:
                    if(input == getShiftExpr()){
                        input = 1;
                    }else {
                        input = 0;
                    }
                    break;
            }
        }

        return input;
    }

    int getAndOrExpr() throws Exception {
        return getCompareExpr();
    }

    int getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
