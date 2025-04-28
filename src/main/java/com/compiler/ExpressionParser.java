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
        return getPlusMinusExpr();
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
        ASTExprNode predicate = getCompareExpr();

        if(m_lexer.lookAhead().m_type == TokenIntf.Type.QUESTIONMARK) {
            m_lexer.advance();
            ASTExprNode operand1 = getQuestionMarkExpr();
            m_lexer.expect(TokenIntf.Type.DOUBLECOLON);
            ASTExprNode operand2 = getQuestionMarkExpr();
            return new ASTQuestionMarkNode(predicate, operand1, operand2);
        }else{
            return predicate;
        }
    }

}