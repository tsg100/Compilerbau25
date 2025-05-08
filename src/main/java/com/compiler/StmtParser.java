package com.compiler;
import com.compiler.TokenIntf.Type;
import com.compiler.ast.*;

public class StmtParser {
    private Lexer m_lexer;
    private ExpressionParser m_exprParser;
    private SymbolTable m_symbolTable;

    public StmtParser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = new SymbolTable();
        m_exprParser = new ExpressionParser(lexer);
    }

    public ASTStmtNode parseProgram(String program) throws Exception {
        m_lexer.init(program);
        return parseBlockStmt();
    }
    
    public ASTStmtNode parseBlockStmt() throws Exception {
        return null;
    }

    public ASTStmtNode parseStmtlist() throws Exception {
        return null;
    }

    public ASTStmtNode parseStmt() throws Exception {
        return null;
    }

    public ASTStmtNode parsePrintStmt() throws Exception {
        return null;
    }

    public ASTStmtNode parseAssignStmt() throws Exception {
    	
    	m_lexer.expect(TokenIntf.Type.IDENT);        
    	String identifier = m_lexer.m_currentToken.m_value;
    	Symbol ident = m_symbolTable.getSymbol(identifier);
    	if (ident != null) {
    		m_lexer.advance(); // ASSIGN
    		ASTExprNode expr = m_exprParser.getQuestionMarkExpr();
    		return new ASTAssignStmtNode(ident, expr);
		}
    	else {
			m_lexer.throwCompilerException(String.format("%s not declared" , identifier), "");
		}
    	
        return null;
    }

    public ASTStmtNode parseDeclareStmt() throws Exception {
        return null;
    }
}
