package com.compiler;
import com.compiler.TokenIntf.Type;
import com.compiler.ast.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private Lexer m_lexer;
    private SymbolTableIntf m_symbolTable;
    private FunctionTableIntf m_functionTable;

    public ExpressionParser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = new SymbolTable();
        m_functionTable = new FunctionTable();
    }

    public ExpressionParser(Lexer lexer, SymbolTableIntf symbolTable, FunctionTableIntf functionTable) {
        m_lexer = lexer;
        m_symbolTable = symbolTable;
        m_functionTable = functionTable;
    }

    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    ASTExprNode getParantheseExpr() throws Exception {
        // parentheseExpr : INTEGER
        // parentheseExpr : LPAREN questionMarkExpr RPAREN
        Token curToken = m_lexer.lookAhead();
        if (curToken.m_type == TokenIntf.Type.INTEGER) {
            m_lexer.advance(); // INTEGER
            ASTExprNode result = new ASTIntegerLiteralNode(curToken.m_value);
            return result;
        } else if (curToken.m_type == TokenIntf.Type.LPAREN) {
            m_lexer.advance(); // LPAREN
            ASTExprNode result = getQuestionMarkExpr();
            m_lexer.expect(TokenIntf.Type.RPAREN);
            return result;
        } else if (curToken.m_type == TokenIntf.Type.IDENT) {
            return getVariableExpr();
        } else {
            m_lexer.throwCompilerException("unexpected symbol", "INTEGER or LPAREN");
            return null;
        }
    }

    ASTExprNode getVariableExpr() throws Exception {
        Token curToken = m_lexer.lookAhead();
        if (curToken.m_type == Type.IDENT) {
            m_lexer.advance();
            Symbol symbol = m_symbolTable.getSymbol(curToken.m_value);
            if (symbol == null)
                throw new CompilerException("Variable not declared: " + curToken.m_value, curToken.m_firstLine, "<line>", "a declared variable");
            return new ASTVariableExprNode(symbol);
        }
        throw new CompilerException("Expected an variable Identifier: " + curToken.m_value, curToken.m_firstLine, "<line>", "a Variable Identifier");
    }

    ASTExprNode getDashExpr() throws Exception {
        ASTExprNode result = getCallExpr();
        while (m_lexer.lookAhead().m_type == Type.TDASH) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getCallExpr();
            result = new ASTTDashNode(result, operand);
        }
        return result;
    }

    ASTExprNode getUnaryExpr() throws Exception {
        switch (m_lexer.lookAhead().m_type) {
            case MINUS -> {
                m_lexer.advance();
                return new ASTUnaryExprNode(getUnaryExpr(), TokenIntf.Type.MINUS);
            }
            case NOT -> {
                m_lexer.advance();
                return new ASTUnaryExprNode(getUnaryExpr(), TokenIntf.Type.NOT);
            }
            default -> {
                return getDashExpr();
            }
        }
    }

    ASTExprNode getMulDivExpr() throws Exception {
        ASTExprNode operand1 = getUnaryExpr();

        while (m_lexer.lookAhead().m_type == TokenIntf.Type.MUL || m_lexer.lookAhead().m_type == TokenIntf.Type.DIV) {
            var operandToken = m_lexer.lookAhead();

            m_lexer.advance();

            ASTExprNode operand2 = getUnaryExpr();

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

        while (m_lexer.lookAhead().m_type == Type.BITAND) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getPlusMinusExpr();
            result = new ASTBitAndExprNode(result, operand, curToken.m_type);
        }
        return result;
    }


    ASTExprNode getBitOrExpr() throws Exception {
        ASTExprNode result = getBitAndExpr();

        while (m_lexer.lookAhead().m_type == Type.BITOR) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getBitAndExpr();
            result = new ASTBitOrExprNode(result, operand, curToken.m_type);
        }
        return result;
    }


    ASTExprNode getShiftExpr() throws Exception {
        ASTExprNode result = getBitAndOrExpr();

        while (m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTLEFT || m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTRIGHT) {
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance(); // PLUS|MINUS
            ASTExprNode operand = getBitAndOrExpr();
            result = new ASTShiftExprNode(result, operand, curToken.m_type);
        }
        return result;
    }

    ASTExprNode getCompareExpr() throws Exception {
        ASTExprNode result = getShiftExpr();
        while (
                m_lexer.lookAhead().m_type == Type.EQUAL ||
                        m_lexer.lookAhead().m_type == Type.GREATER ||
                        m_lexer.lookAhead().m_type == Type.LESS
        ){
            Token curToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode operand = getShiftExpr();
            result = new ASTCompareExprNode(result, operand, curToken.m_type);
        }

        return result;
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

    ASTExprNode getCallExpr() throws Exception {
        // callExpr: CALL IDENTIFIER LPAREN argList RPAREN
        if(m_lexer.m_currentToken.m_type != TokenIntf.Type.CALL) {
            return getParantheseExpr();
        }else {
            m_lexer.advance();
            String funcName = m_lexer.m_currentToken.m_value;
            m_lexer.expect(TokenIntf.Type.IDENT);
            FunctionInfo functionInfo = m_functionTable.getFunction(funcName);
            if(functionInfo == null) {
                m_lexer.throwCompilerException(String.format("%s not declared" , funcName), "");
            }

            m_lexer.expect(TokenIntf.Type.LPAREN);
            List<ASTExprNode> argumentList = getArgumentList();
            if(argumentList.size() != functionInfo.varNames.size()){
                m_lexer.throwCompilerException(String.format("%s function parameters for function %s" , argumentList.size() < functionInfo.varNames.size()? "Not enough": "To many", funcName), "");
            }

            m_lexer.expect(TokenIntf.Type.RPAREN);
            return new ASTCallExprNode(funcName, argumentList);
        }

    }

    private List<ASTExprNode> getArgumentList() throws Exception {
        // argList: expr argListPost | eps
        // argListPost: eps | COMMA expr argListPost
        List<ASTExprNode> argumentList = new ArrayList<>();
        if(m_lexer.m_currentToken.m_type == Type.RPAREN){
            return argumentList;
        }else {
            argumentList.add(getQuestionMarkExpr());
            while (m_lexer.m_currentToken.m_type == Type.COMMA) {
                m_lexer.advance();
                argumentList.add(getQuestionMarkExpr());
            }
            return argumentList;
        }
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {
        ASTExprNode predicate = getAndOrExpr();

        if (m_lexer.lookAhead().m_type == TokenIntf.Type.QUESTIONMARK) {
            m_lexer.advance();
            ASTExprNode operand1 = getQuestionMarkExpr();
            m_lexer.expect(TokenIntf.Type.DOUBLECOLON);
            ASTExprNode operand2 = getQuestionMarkExpr();
            return new ASTQuestionMarkNode(predicate, operand1, operand2);
        } else {
            return predicate;
        }
    }

}
