package com.compiler;

import com.compiler.TokenIntf.Type;

public class ExpressionEvaluator implements ExpressionEvaluatorIntf {
    private Lexer m_lexer;

    public ExpressionEvaluator(final Lexer lexer) {
        m_lexer = lexer;
    }

    @Override
    public int eval(final String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    int getParantheseExpr() throws Exception {
        final Token curToken = m_lexer.lookAhead();
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
        
        int result = getUnaryExpr();

        while (
            m_lexer.lookAhead().m_type == TokenIntf.Type.MUL ||
            m_lexer.lookAhead().m_type == TokenIntf.Type.DIV
) {
            TokenIntf.Type operator = m_lexer.lookAhead().m_type;

            m_lexer.advance();

            int operand2 = getUnaryExpr();

            
            if (operator == TokenIntf.Type.MUL) {
                result = result * operand2;
            }
            else if (operator == TokenIntf.Type.DIV) {
                result = result / operand2;
            } else {
                throw new Exception("Expected operator of type MUL or DIV got " + operator);
            }
            
        }

        return result;
    }
   
    int getPlusMinusExpr() throws Exception {
        // sumExpr: mulExpr (sumOp mulExpr)*
        int result = getMulDivExpr();
        while (
            m_lexer.lookAhead().m_type == TokenIntf.Type.PLUS ||
            m_lexer.lookAhead().m_type == TokenIntf.Type.MINUS
        ) {
            final TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
            m_lexer.advance(); // getSumOp()
            final int op = getMulDivExpr();
            if (tokenType == TokenIntf.Type.PLUS) {
                result += op;
            } else {
                result -= op;
            }
        }
        return result;
    }

    int getBitAndOrExpr() throws Exception {
        int result = getPlusMinusExpr();
        while(
            m_lexer.lookAhead().m_type == TokenIntf.Type.BITAND ||
            m_lexer.lookAhead().m_type == TokenIntf.Type.BITOR
        ) {
            final TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
            m_lexer.advance();
            final int op = getPlusMinusExpr();
            if (tokenType == TokenIntf.Type.BITAND) {
                result &= op;
            } else {
                result |= op;
            }
        }
        return result;
    }

    int getShiftExpr() throws Exception {
    	// shiftExpr = bitAndOrExpr shiftExprRecursive
    	int result = getBitAndOrExpr();
        while (
            m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTRIGHT ||
            m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTLEFT
        ) {
            TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
            m_lexer.advance(); // getShiftOp()
            int op = getBitAndOrExpr();
            if (tokenType == TokenIntf.Type.SHIFTRIGHT) {
                result = result >> op;
            } else {
            	result = result << op;
            }
        }
        return result;
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
