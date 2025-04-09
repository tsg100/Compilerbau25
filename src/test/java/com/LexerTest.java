package com;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LexerTest extends LexerTestBase
{
    @Test
    public void shouldAnswerWithTrue() throws Exception
    {
        testLexer("AABB", "IDENT AABB\n");
    }
}
