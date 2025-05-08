package com.compiler;

import java.util.ArrayList;
import java.util.List;

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
        m_lexer.expect(TokenIntf.Type.LBRACE);
        ASTStmtNode result = parseStmtlist();
        m_lexer.expect(TokenIntf.Type.RBRACE);
        return result;
    }

    public ASTStmtNode parseStmtlist() throws Exception {
        Token curToken = m_lexer.lookAhead();
        final List<ASTStmtNode> stmtList = new ArrayList<>();

        while (curToken.m_type == Type.DECLARE || curToken.m_type == Type.IDENT || curToken.m_type == Type.PRINT) {
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
        return null;
    }

    public ASTStmtNode parseDeclareStmt() throws Exception {
        return null;
    }
}
