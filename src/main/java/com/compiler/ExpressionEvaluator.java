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
        return getCompareExpr();
    }

    int getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
