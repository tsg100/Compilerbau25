package com.compiler;

import java.util.ArrayList;
import java.util.List;

import com.compiler.TokenIntf.Type;
import com.compiler.ast.*;

public class StmtParser {
    private Lexer m_lexer;
    private ExpressionParser m_exprParser;
    private SymbolTable m_symbolTable;
    private FunctionTable m_functionTable;

    public StmtParser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = new SymbolTable();
        m_exprParser = new ExpressionParser(lexer, m_symbolTable);
        m_functionTable = null;
    }

    public StmtParser(Lexer lexer, SymbolTable symbolTable, FunctionTable functionTable) {
        m_lexer = lexer;
        m_symbolTable = symbolTable;
        m_functionTable = functionTable;
        m_exprParser = new ExpressionParser(lexer, m_symbolTable);
    }

    public ASTStmtNode parseProgram(String program) throws Exception {
        m_lexer.init(program);
        return parseBlockStmt();
    }

    public ASTStmtNode parseBlockStmt() throws Exception {
        m_lexer.expect(TokenIntf.Type.LBRACE);
        ASTStmtNode result = parseStmtlist();
        m_lexer.expect(TokenIntf.Type.RBRACE);
        return result;
    }

    public ASTStmtListNode parseStmtlist() throws Exception {
        Token curToken = m_lexer.lookAhead();
        final List<ASTStmtNode> stmtList = new ArrayList<>();

        while (curToken.m_type != com.compiler.TokenIntf.Type.RBRACE) {
            stmtList.add(parseStmt());
            curToken = m_lexer.lookAhead();
        }
        return new ASTStmtListNode(stmtList);
    }

    public ASTStmtNode parseStmt() throws Exception {
        Token token = m_lexer.lookAhead();
        TokenIntf.Type type = token.m_type;

        if (type == TokenIntf.Type.DECLARE) {
            return parseDeclareStmt();
        }
        if (type == TokenIntf.Type.IDENT) {
            return parseAssignStmt();
        }
        if (type == TokenIntf.Type.PRINT) {
            return parsePrintStmt();
        }
        if (type == TokenIntf.Type.BLOCK) {
            return parseJumpBlockStmt();
        }
        if (type == TokenIntf.Type.EXECUTE) {
        	return parseExecuteNTimesStmt();
        }

        m_lexer.throwCompilerException("Invalid begin of statement", "DECLARE or IDENTIFIER or PRINT");
        return null; // unreachable
    }

    public ASTStmtNode parsePrintStmt() throws Exception {
        m_lexer.expect(Type.PRINT);

        ASTPrintStmtNode astPrintStmtNode = new ASTPrintStmtNode(m_exprParser.getQuestionMarkExpr());
        m_lexer.expect(Type.SEMICOLON);
        return astPrintStmtNode;
    }

    public ASTStmtNode parseAssignStmt() throws Exception {
    	
    	String identifier = m_lexer.m_currentToken.m_value;
    	m_lexer.expect(TokenIntf.Type.IDENT);        
    	Symbol ident = m_symbolTable.getSymbol(identifier);
    	if (ident != null) {
    		m_lexer.advance(); // ASSIGN
    		ASTExprNode expr = m_exprParser.getQuestionMarkExpr();
            m_lexer.expect(TokenIntf.Type.SEMICOLON);
    		return new ASTAssignStmtNode(ident, expr);
		}
    	else {
			m_lexer.throwCompilerException(String.format("%s not declared" , identifier), "");
		}
    	
        return null;
    }

    public ASTStmtNode parseDeclareStmt() throws Exception {

        // Consume declare
        m_lexer.expect(TokenIntf.Type.DECLARE);


        if(m_lexer.m_currentToken.m_type != TokenIntf.Type.IDENT) {
            throw new Exception("Expected token of type Identifier");
        }

        String identifier = m_lexer.m_currentToken.m_value;

        if(m_symbolTable.getSymbol(identifier) != null ){
            throw new Exception("Variable was already declared: "+ identifier);
        }
        
        m_symbolTable.createSymbol(identifier);
        
        m_lexer.advance(); // IDENT

        m_lexer.expect(TokenIntf.Type.SEMICOLON);


        return new ASTDeclareStmtNode(identifier);

    }

    ASTStmtNode parseJumpBlockStmt() throws Exception {
        // BLOCK LBRACE stmtList RBRACE
        m_lexer.expect(Type.BLOCK);
        m_lexer.expect(Type.LBRACE);

        ASTStmtListNode stmtlistNode = parseStmtlist();

        m_lexer.expect(Type.RBRACE);

        return new ASTJumpBlockNode(stmtlistNode);
    }

    ASTStmtNode parseExecuteNTimesStmt() throws Exception {
    	// EXECUTE integer|identifier TIMES LBRACE stmtList RBRACE
    	m_lexer.expect(Type.EXECUTE);
    	
    	if(m_lexer.m_currentToken.m_type != Type.INTEGER && m_lexer.m_currentToken.m_type != Type.IDENT) {
            throw new Exception("Expected token of type Integer or Identifier");
        }
    	ASTExprNode count;
    	
    	count = m_exprParser.getQuestionMarkExpr();
    	
    	m_lexer.expect(Type.TIMES);
    	m_lexer.expect(Type.LBRACE);
    	
    	ASTStmtListNode stmtlistNode = parseStmtlist();
    	
    	
    	m_lexer.expect(Type.RBRACE);
    	m_lexer.expect(Type.SEMICOLON);
    	
    	return new ASTExecuteNTimesNode(count, stmtlistNode);
    }
    
    
}
