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
        m_functionTable = new FunctionTable();
        m_exprParser = new ExpressionParser(lexer, m_symbolTable, m_functionTable);
    }

    public StmtParser(Lexer lexer, SymbolTable symbolTable, FunctionTable functionTable) {
        m_lexer = lexer;
        m_symbolTable = symbolTable;
        m_functionTable = functionTable;
        m_exprParser = new ExpressionParser(lexer, m_symbolTable, m_functionTable);
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
        if (type == Type.FUNCTION) {
            return parseFunctionStmt();
        }      
        if (type == TokenIntf.Type.WHILE) {
            return parseWhileLoopStmt();
        }
        if (type == TokenIntf.Type.DO) {
            return parseDoWhileLoopStmt();
        }
      
        if (type == TokenIntf.Type.EXECUTE) {
        	return parseExecuteNTimesStmt();
        }

        if (type == TokenIntf.Type.NUMERIC_IF) {
            return parseNumericIfStmt();
        }

        m_lexer.throwCompilerException("Invalid begin of statement", "DECLARE or IDENTIFIER or PRINT or NUMERIC_IF");
        return null; // unreachable
    }

    public ASTStmtNode parseFunctionStmt() throws Exception {
        m_lexer.expect(Type.FUNCTION);
        String functionName = m_lexer.m_currentToken.m_value;
        m_lexer.expect(TokenIntf.Type.IDENT);
        m_lexer.expect(Type.LPAREN);
        List<String> parameterList = parseParameterList();
        parameterList.forEach(e -> m_symbolTable.createSymbol(e));
        m_lexer.expect(TokenIntf.Type.RPAREN);

        m_lexer.expect(TokenIntf.Type.LBRACE);
        List<ASTStmtNode> functionBody = parseFunctionBody();
        m_lexer.expect(TokenIntf.Type.RBRACE);
        m_lexer.expect(Type.SEMICOLON);
        m_functionTable.createFunction(functionName, parameterList);
        return new ASTFunctionStmtNode(functionName, parameterList, functionBody);
    }

    private List<ASTStmtNode> parseFunctionBody() throws Exception {
        List<ASTStmtNode> stmtList = new ArrayList<>();
        while(m_lexer.m_currentToken.m_type != Type.RETURN){
            stmtList.add(parseStmt());
        }
        stmtList.add(parseReturnStmt());
        return stmtList;
    }

    private ASTStmtNode parseReturnStmt() throws Exception {
        m_lexer.expect(Type.RETURN);
        ASTStmtNode returnStmtNode = new ASTReturnStmtNode(m_exprParser.getQuestionMarkExpr());
        m_lexer.expect(Type.SEMICOLON);
        return returnStmtNode;
    }

    private List<String> parseParameterList() throws Exception {
        List<String> parameterList = new ArrayList<>();
        if(m_lexer.m_currentToken.m_type != Type.IDENT){
            return parameterList;
        } else {
            parameterList.add(m_lexer.m_currentToken.m_value);
            m_lexer.advance();

            while (m_lexer.m_currentToken.m_type == Type.COMMA) {
                m_lexer.advance();
                parameterList.add(m_lexer.m_currentToken.m_value);
                m_lexer.expect(Type.IDENT);
            }
            return parameterList;
        }
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
        } else {
            m_lexer.throwCompilerException(String.format("%s not declared", identifier), "");
        }

        return null;
    }

    public ASTStmtNode parseDeclareStmt() throws Exception {

        // Consume declare
        m_lexer.expect(TokenIntf.Type.DECLARE);

        if (m_lexer.m_currentToken.m_type != TokenIntf.Type.IDENT) {
            throw new Exception("Expected token of type Identifier");
        }

        String identifier = m_lexer.m_currentToken.m_value;

        if (m_symbolTable.getSymbol(identifier) != null) {
            throw new Exception("Variable was already declared: " + identifier);
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


    ASTStmtNode parseNumericIfStmt() throws Exception {
        // NUMERIC_IF LPAR expr RPAR numericIfBlock
        m_lexer.expect(Type.NUMERIC_IF);
        m_lexer.expect(Type.LPAREN);
        final ASTExprNode predicate = this.m_exprParser.getQuestionMarkExpr();
        m_lexer.expect(Type.RPAREN);

        // numericIfBlock positiveBlock negativeBlock zeroBlock
        // positiveBlock: POSITIVE LBRACE stmtlist RBRACE
        final ASTStmtListNode positiveStmtList = parseNumericIfBlock(Type.POSITIVE);

        // negativeBlock: NEGATIVE LBRACE stmtlist RBRACE
        final ASTStmtListNode negativeStmtList = parseNumericIfBlock(Type.NEGATIVE);

        // zeroBlock: ZERO LBRACE stmtlist RBRACE
        final ASTStmtListNode zeroStmtList = parseNumericIfBlock(Type.ZERO);

        return new ASTNumericIfNode(predicate, positiveStmtList, negativeStmtList, zeroStmtList);
    }


    private ASTStmtListNode parseNumericIfBlock(final Type type) throws Exception {
        m_lexer.expect(type);
        m_lexer.expect(Type.LBRACE);
        final ASTStmtListNode stmtList = parseStmtlist();
        m_lexer.expect(Type.RBRACE);
        return stmtList;
    }


    ASTWhileLoopStmtNode parseWhileLoopStmt() throws Exception {
        // whileStmt: WHILE LPAREN questionMarkExpr RPAREN blockStmt SEMICOLON
        m_lexer.expect(TokenIntf.Type.WHILE);
        m_lexer.expect(TokenIntf.Type.LPAREN);
        ASTExprNode predicate = this.m_exprParser.getQuestionMarkExpr();
        m_lexer.expect(TokenIntf.Type.RPAREN);

        ASTStmtNode body = parseBlockStmt();
        m_lexer.expect(Type.SEMICOLON);
        return new ASTWhileLoopStmtNode(predicate, body);

    }

    ASTDoWhileLoopStmtNode parseDoWhileLoopStmt() throws Exception {
        // doWhileStmt: DO blockStmt WHILE questionMarkExpr SEMICOLON

        m_lexer.expect(Type.DO);
        ASTStmtNode body = parseBlockStmt();
        m_lexer.expect(Type.WHILE);
        m_lexer.expect(Type.LPAREN);
        ASTExprNode predicate = this.m_exprParser.getQuestionMarkExpr();
        m_lexer.expect(Type.RPAREN);
        m_lexer.expect(Type.SEMICOLON);
        return new ASTDoWhileLoopStmtNode(predicate, body);
    }
    
    ASTStmtNode parseExecuteNTimesStmt() throws Exception {
    	// EXECUTE integer|identifier TIMES LBRACE stmtList RBRACE SEMICOLON
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