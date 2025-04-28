package com.compiler;
import com.compiler.TokenIntf.Type;
import com.compiler.ast.*;

public class ExpressionParser {
    private Lexer m_lexer;

    public ExpressionParser(Lexer lexer) {
        m_lexer = lexer;
    }
    
    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    ASTExprNode getParantheseExpr() throws Exception {
        // parentheseExpr : INTEGER
        Token curToken = m_lexer.lookAhead();
        m_lexer.expect(TokenIntf.Type.INTEGER);
        ASTExprNode result = new ASTIntegerLiteralNode(curToken.m_value);
        return result;
    }

    ASTExprNode getDashExpr() throws Exception {
        return getParantheseExpr();
     }    
     
    ASTExprNode getUnaryExpr() throws Exception {
       return getDashExpr();
    }
    
    ASTExprNode getMulDivExpr() throws Exception {
       return getUnaryExpr();
    }
    
    ASTExprNode getPlusMinusExpr() throws Exception {
        // plusMinusExpr: mulDivExpr ((PLUS|MINUS) mulDivExpr)*
        ASTExprNode result = getMulDivExpr();
        // SELECTION SET for (PLUS|MINUS) mulDivExpr
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.PLUS || m_lexer.lookAhead().m_type == TokenIntf.Type.MINUS) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance(); // PLUS|MINUS
            ASTExprNode operand = getMulDivExpr();
            result = new ASTPlusMinusExprNode(result, operand, curToken.m_type);
        }
        return result;
    }

    ASTExprNode getBitAndOrExpr() throws Exception {
        // bitExpr: plusMinusExpr ((AND|OR) bitExpr)*
        // ACHTUNG: & hat höhere Priorität, daher haben wir zwei einzelne Funktionen erstellt.
        // Das sich für die anderen nichts ändert, geben wir hier einfach das bitOr zurück!
        return getBitOrExpr();
    }


    ASTExprNode getBitAndExpr() throws Exception {
        ASTExprNode result = getPlusMinusExpr();

        while(m_lexer.lookAhead().m_type == Type.BITAND) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getPlusMinusExpr();
            result = new ASTBitAndExprNode(result, operand, curToken.m_type);
        }
        return result;
    }


    ASTExprNode getBitOrExpr() throws Exception {
        ASTExprNode result = getBitAndExpr();

        while(m_lexer.lookAhead().m_type == Type.BITOR) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getBitAndExpr();
            result = new ASTBitOrExprNode(result, operand, curToken.m_type);
        }
        return result;
    }


    ASTExprNode getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    ASTExprNode getCompareExpr() throws Exception {
        return getShiftExpr();
    }

    ASTExprNode getAndOrExpr() throws Exception {
        return getCompareExpr();
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }

}
