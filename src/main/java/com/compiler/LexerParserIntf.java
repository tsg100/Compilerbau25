package com.compiler;

public interface LexerParserIntf {
    /**
     * returns the token at the current head of the input stream
     */
    public Token lookAhead();

    /**
     * consumes the current token and reads the next one
     */
    public void advance() throws Exception;

    /**
     * checks if the the current head token has the expected tokenType
     * if yes, the current head token is consumed
     * otherwise an exception is thrown
     */
    public void expect(Token.Type tokenType) throws Exception;

    /**
     * if the current head token has the passed tokenType,
     * the current head token is consumed and the method returns true
     * otherwise returns false without changing the current head token
     */
    public boolean accept(Token.Type tokenType) throws Exception;

}
