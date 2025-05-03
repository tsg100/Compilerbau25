package com.compiler;
import com.compiler.TokenIntf.Type;
import com.compiler.ast.*;

public class ExpressionParser {
    private Lexer m_lexer;
    private SymbolTableIntf m_symbolTable;

    public ExpressionParser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = new SymbolTable();
    }
    
    public ExpressionParser(Lexer lexer, SymbolTableIntf symbolTable) {
        m_lexer = lexer;
        m_symbolTable = symbolTable;
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

    ASTExprNode getVariableExpr() throws Exception {
        return null;
    }

    ASTExprNode getDashExpr() throws Exception {
        return getParantheseExpr();
     }    
     
    ASTExprNode getUnaryExpr() throws Exception {
       return getDashExpr();
    }
    
    ASTExprNode getMulDivExpr() throws Exception {
       ASTExprNode operand1 =  getUnaryExpr();

       while(m_lexer.lookAhead().m_type == TokenIntf.Type.MUL || m_lexer.lookAhead().m_type == TokenIntf.Type.DIV) {
            var operandToken = m_lexer.lookAhead();

            m_lexer.advance();

            ASTExprNode operand2 =  getUnaryExpr();

            operand1 = new ASTMulDivExprNode(operand1, operand2, operandToken.m_type);

       }
       return operand1;
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

    ASTPlusMinusExprRecursiveNode getPlusMinusExprRecursive() throws Exception {
        // plusMinusExprRecursive: (PLUS|MINUS) mulDivExpr plusMinusExprRecursive
        // plusMinusExprRecursive: eps
        if (m_lexer.lookAhead().m_type == TokenIntf.Type.PLUS || m_lexer.lookAhead().m_type == TokenIntf.Type.MINUS) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance(); // PLUS|MINUS
            ASTExprNode operand0 = getMulDivExpr();
            ASTPlusMinusExprRecursiveNode operand1 = getPlusMinusExprRecursive();
            return new ASTPlusMinusExprRecursiveNode(curToken.m_type, operand0, operand1);
        } else {
            return null;
        }
    }

    ASTExprNode getPlusMinusExprWithRecursion() throws Exception {
        // plusMinusExpr: mulDivExpr plusMinusExprRecursive
        ASTExprNode operand0 = getMulDivExpr();
        ASTPlusMinusExprRecursiveNode operand1 = getPlusMinusExprRecursive();
        return new ASTPlusMinusExprWithRecursionNode(operand0, operand1);
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

    ASTExprNode getAndExpr() throws Exception {
        // andExpr: compareExpr (AND compareExpr)*
        ASTExprNode result = getCompareExpr();
        // SELECTION SET for AND compareExpr
        while (m_lexer.lookAhead().m_type == Type.AND) {
            m_lexer.advance(); // AND
            result = new ASTAndOrExprNode(result, getCompareExpr(), Type.AND);
        }
        return result;
    }

    ASTExprNode getOrExpr() throws Exception {
        // orExpr: andExpr (OR andExpr)*
        ASTExprNode result = getAndExpr();
        // SELECTION SET for OR andExpr
        while (m_lexer.lookAhead().m_type == Type.OR) {
            m_lexer.advance(); // OR
            result = new ASTAndOrExprNode(result, getAndExpr(), Type.OR);
        }
        return result;
    }

    ASTExprNode getAndOrExpr() throws Exception {
        //handled separately
        return getOrExpr();
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }

}
