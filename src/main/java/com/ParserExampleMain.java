package com;


public class ParserExampleMain {
    com.compiler.Lexer m_lexer;

    void parsePrint() throws Exception {
        m_lexer.expect(com.compiler.TokenIntf.Type.PRINT);
        com.compiler.Token number = m_lexer.lookAhead();
        m_lexer.expect(com.compiler.TokenIntf.Type.INTEGER);
        m_lexer.expect(com.compiler.TokenIntf.Type.SEMICOLON);
        System.out.println(number.m_value);
    }

    void parsePrintList() throws Exception {
        if (m_lexer.lookAhead().m_type != com.compiler.TokenIntf.Type.EOF) {
            parsePrint();
            parsePrintList();
        }
    }

    void parse(String input) throws Exception {
        m_lexer = new com.compiler.Lexer();
        m_lexer.init(input);
        parsePrintList();
    }

    public static void main(String[] args) throws Exception {
        ParserExampleMain parser = new ParserExampleMain();
        parser.parse("""
                PRINT 1;
                PRINT 2;
                PRINT 3;""");
    }

}
