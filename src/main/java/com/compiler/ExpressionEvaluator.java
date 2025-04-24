package com.compiler;

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
        return getShiftExpr();
    }

    int getAndOrExpr() throws Exception {
        //handled separately
        return getOrExpr();
    }

    int getOrExpr() throws Exception {
        // orExpr: andExpr (orOp andExpr)*
        int result = getAndExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.OR) {
            m_lexer.advance(); // getOrOp()
            int op = getAndExpr();
            result = result == 0 && op == 0 ? 0 : 1;
        }
        return result;
    }

    int getAndExpr() throws Exception {
        // andExpr: compareExpr (andOp compareExpr)*
        int result = getCompareExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.AND) {
            m_lexer.advance(); // getAndOp()
            int op = getAndExpr();
            result = result == 0 || op == 0 ? 0 : 1;
        }
        return result;
    }

    int getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
