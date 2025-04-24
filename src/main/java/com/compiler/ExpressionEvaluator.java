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
        int result = getMulDivExpr();
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
        return getCompareExpr();
    }

    int getQuestionMarkExpr() throws Exception {
        int result = getAndOrExpr();
        boolean predicate = result != 0;
        
        if(m_lexer.lookAhead().m_type == TokenIntf.Type.QUESTIONMARK) {
            m_lexer.advance();
            int op1 = getQuestionMarkExpr();

            if(m_lexer.lookAhead().m_type != TokenIntf.Type.DOUBLECOLON) throw new Exception("Colon expected in ternary operator");
            
            m_lexer.advance();
            int op2 = getQuestionMarkExpr();



            return predicate ? op1: op2;
        } else {
            return result;
        }
    }
}
