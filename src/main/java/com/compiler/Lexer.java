package com.compiler;

import java.io.OutputStreamWriter;
import java.util.Vector;

import com.compiler.machines.ABMachine;

public class Lexer implements LexerIntf {

    static class MachineInfo {

        public StateMachineIntf m_machine;
        public int m_acceptPos;

        public MachineInfo(StateMachineIntf machine) {
            m_machine = machine;
            m_acceptPos = 0;
        }

        public void init(String input) {
            m_acceptPos = 0;
            m_machine.init(input);
        }
    }

    protected Vector<MachineInfo> m_machineList;
    protected MultiLineInputReader m_input;
    protected Token m_currentToken;

    public Lexer() {
        m_machineList = new Vector<MachineInfo>();
        addLexerMachines();
    }

    private void addLexerMachines() {
        addMachine(new com.compiler.machines.BlockCommentMachine());
        addMachine(new com.compiler.machines.Characterliteral());

        addKeywordMachine("*", com.compiler.TokenIntf.Type.MUL);
        addKeywordMachine("/", com.compiler.TokenIntf.Type.DIV);
        addKeywordMachine("+", com.compiler.TokenIntf.Type.PLUS);
        addKeywordMachine("-", com.compiler.TokenIntf.Type.MINUS);
        addKeywordMachine("&", com.compiler.TokenIntf.Type.BITAND);
        addKeywordMachine("|", com.compiler.TokenIntf.Type.BITOR);
        addKeywordMachine("<<", com.compiler.TokenIntf.Type.SHIFTLEFT);
        addKeywordMachine(">>", com.compiler.TokenIntf.Type.SHIFTRIGHT);
        addKeywordMachine("==", com.compiler.TokenIntf.Type.EQUAL);
        addKeywordMachine("<", com.compiler.TokenIntf.Type.LESS);
        addKeywordMachine(">", com.compiler.TokenIntf.Type.GREATER);
        addKeywordMachine("!", com.compiler.TokenIntf.Type.NOT);
        addKeywordMachine("&&", com.compiler.TokenIntf.Type.AND);
        addKeywordMachine("||", com.compiler.TokenIntf.Type.OR);
        addKeywordMachine("?", com.compiler.TokenIntf.Type.QUESTIONMARK);
        addKeywordMachine(":", com.compiler.TokenIntf.Type.DOUBLECOLON);
        addKeywordMachine("(", com.compiler.TokenIntf.Type.LPAREN);
        addKeywordMachine(")", com.compiler.TokenIntf.Type.RPAREN);
        addKeywordMachine("{", com.compiler.TokenIntf.Type.LBRACE);
        addKeywordMachine("}", com.compiler.TokenIntf.Type.RBRACE);
        addKeywordMachine(";", com.compiler.TokenIntf.Type.SEMICOLON);
        addKeywordMachine(",", com.compiler.TokenIntf.Type.COMMA);
        addKeywordMachine("=", com.compiler.TokenIntf.Type.ASSIGN);

        addKeywordMachine("DECLARE", com.compiler.TokenIntf.Type.DECLARE);
        addKeywordMachine("PRINT", com.compiler.TokenIntf.Type.PRINT);
        addKeywordMachine("IF", com.compiler.TokenIntf.Type.IF);
        addKeywordMachine("ELSE", com.compiler.TokenIntf.Type.ELSE);
        addKeywordMachine("WHILE", com.compiler.TokenIntf.Type.WHILE);
        addKeywordMachine("DO", com.compiler.TokenIntf.Type.DO);
        addKeywordMachine("FOR", com.compiler.TokenIntf.Type.FOR);
        addKeywordMachine("LOOP", com.compiler.TokenIntf.Type.LOOP);
        addKeywordMachine("ENDLOOP", TokenIntf.Type.ENDLOOP);
        addKeywordMachine("BREAK", com.compiler.TokenIntf.Type.BREAK);
        addKeywordMachine("SWITCH", com.compiler.TokenIntf.Type.SWITCH);
        addKeywordMachine("CASE", com.compiler.TokenIntf.Type.CASE);
        addKeywordMachine("EXECUTE", com.compiler.TokenIntf.Type.EXECUTE);
        addKeywordMachine("TIMES", com.compiler.TokenIntf.Type.TIMES);
        addKeywordMachine("FUNCTION", com.compiler.TokenIntf.Type.FUNCTION);
        addKeywordMachine("CALL", com.compiler.TokenIntf.Type.CALL);
        addKeywordMachine("RETURN", com.compiler.TokenIntf.Type.RETURN);
        addKeywordMachine("BLOCK", com.compiler.TokenIntf.Type.BLOCK);
        addKeywordMachine("DEFAULT", com.compiler.TokenIntf.Type.DEFAULT);

        addMachine(new com.compiler.machines.IdentifierMachine());
        addMachine(new com.compiler.machines.IntegerMachine());
        addMachine(new com.compiler.machines.LineCommentMachine());
        addMachine(new com.compiler.machines.StringLiteral());
        addMachine(new com.compiler.machines.WhitespaceMachine());

        // addKeywordMachine("*", com.compiler.TokenIntf.Type.MUL);
        // ...        
        // addMachine(new compiler.machines.IdentifierMachine());
    }

    public void addMachine(StateMachineIntf machine) {
        m_machineList.add(new MachineInfo(machine));
    }

    public void addKeywordMachine(String keyword, TokenIntf.Type tokenType) {
        m_machineList.add(new MachineInfo(new com.compiler.machines.KeywordMachine(keyword, tokenType)));
    }

    public void initMachines(String input) {
        for (MachineInfo machine : m_machineList) {
            machine.init(input);
        }
    }

    public void init(String input) throws Exception {
        m_input = new MultiLineInputReader(input);
        m_currentToken = new Token();
        advance();
    }

    public Token nextToken() throws Exception {
        // check end of file
        // initMachines
        // while at least one machine running
            // foreach machine
                // if machine excluded
                    // continue
                // machine.nextStep()
                // if machine has error
                    // exclude machine
                // if machine accepts
                    // update accept pos for this machine
            // endforeach // machine
        // end while // at least one machine running
        // if no machine has accept postion
            // throw error
        // select machine with highest accept position (if equal first machine wins)
        // create token
            
        return new Token();
    }

    public void processInput(String input, OutputStreamWriter outStream) throws Exception {
        m_input = new MultiLineInputReader(input);
        // while input available
        while (!m_input.isEmpty()) {
            // get next word
            Token curWord = nextToken();
            // break on failure
            if (curWord.m_type == Token.Type.EOF) {
                outStream.write("ERROR\n");
                outStream.flush();
                break;
            } else if (curWord.m_type == Token.Type.WHITESPACE) {
                continue;
            } else {
                // print word
                outStream.write(curWord.toString());
                outStream.write("\n");
                outStream.flush();
            }
        }
    }

    public Token lookAhead() {
        return m_currentToken;
    }

    public void advance() throws Exception {
        m_currentToken = nextToken();
    }

    public void expect(Token.Type tokenType) throws Exception {
        if (tokenType == m_currentToken.m_type) {
            advance();
        } else {
            throw new CompilerException(
                    "Unexpected token " + m_currentToken.toString(),
                    m_input.getLine(), m_input.getMarkedCodeSnippetCurrentPos(),
                    Token.type2String(tokenType));
        }
    }

    public boolean accept(Token.Type tokenType) throws Exception {
        if (tokenType == m_currentToken.m_type) {
            advance();
            return true;
        }
        return false;
    }
    
    public void throwCompilerException(String reason, String expected) throws Exception {
        String codeSnippet = m_input.getMarkedCodeSnippet(m_currentToken.m_firstLine, m_currentToken.m_firstCol, m_currentToken.m_lastLine, m_currentToken.m_lastCol);
        throw new CompilerException(reason, m_currentToken.m_firstLine, codeSnippet, expected);
    }
}
