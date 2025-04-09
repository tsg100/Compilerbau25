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
        addMachine(new ABMachine());
        // addKeywordMachine("*", com.compiler.TokenIntf.Type.MUL);
        // ...        
        // addMachine(new compiler.machines.IdentifierMachine());
    }

    public void addMachine(StateMachineIntf machine) {
        m_machineList.add(new MachineInfo(machine));
    }

    public void addKeywordMachine(String keyword, TokenIntf.Type tokenType) {
        // m_machineList.add(new MachineInfo(new KeywordMachine(keyword, tokenType)));
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
