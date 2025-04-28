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
        // LPAREN questionMarkExpr RPAREN | INTEGER
        Token curToken = m_lexer.lookAhead();
        if (curToken.m_type == TokenIntf.Type.INTEGER) {
            m_lexer.advance(); // INTEGER
            return Integer.valueOf(curToken.m_value);    
        } else if (curToken.m_type == TokenIntf.Type.LPAREN) {
            m_lexer.advance(); // LPAREN
            int result = getQuestionMarkExpr();
            m_lexer.expect(TokenIntf.Type.RPAREN);
            return result;
        } else {
            m_lexer.throwCompilerException("unexpected token", "integer or parenthese");
            return 0;
        }
    }

    int getDashExpr() throws Exception {    // sumExpr: mulExpr (sumOp mulExpr)*
        int result = getParantheseExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.TDASH) {
            m_lexer.advance();
            int op = getParantheseExpr();
            result = (int) Math.pow(result, op);
        }
        return result;
    }

    int getUnaryExpr() throws Exception {
        TokenIntf.Type tokenType = m_lexer.lookAhead().m_type;
        if (tokenType == TokenIntf.Type.MINUS) {
            m_lexer.advance();
            return -getDashExpr();
        } else if (tokenType == TokenIntf.Type.NOT) {
            m_lexer.advance();
            if (getDashExpr() == 0) {
                return 1;
            } else {
                return 0;
            }
        }
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
            } else if (operator == TokenIntf.Type.DIV) {
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
        return getBitOr();
    }

    int getBitAnd() throws Exception {
        // bitAndExpr: plusMinusExpr (BITAND bitAndExpr)*
        // ACHTUNG: & hat höhere Priorität, daher haben wir zwei einzelne Funktionen erstellt.
        // Das sich für die anderen nichts ändert, geben wir hier einfach das bitOr zurück!
        int result = getPlusMinusExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.BITAND) {
            m_lexer.advance();
            int op = getPlusMinusExpr();
            result &= op;
        }
        return result;
    }

    int getBitOr() throws Exception {
        // bitOrExpr: bitAndExpr (BITOR bitOrExpr)*
        int result = getBitAnd();
        while (m_lexer.lookAhead().m_type == Type.BITOR) {
            m_lexer.advance();
            int op = getBitAnd();
            result |= op;
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
        // questionMarkExpr : andOrExpr (QUESTIONMARK questionMarkExpr DOUBLECOLON questionMarkExpr)?
        int result = getAndOrExpr();
        boolean predicate = result != 0;
        
        if(m_lexer.lookAhead().m_type == TokenIntf.Type.QUESTIONMARK) {
            m_lexer.advance(); // QUESTIONMARK
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
