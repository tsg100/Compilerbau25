package com.compiler;

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
        return getUnaryExpr();
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
        return getBitAndOrExpr();
    }

    int getCompareExpr() throws Exception {
        return getShiftExpr();
    }

    int getAndOrExpr() throws Exception {
        return getCompareExpr();
    }

    int getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
