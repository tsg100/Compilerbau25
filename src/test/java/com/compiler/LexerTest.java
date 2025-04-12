package com.compiler;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LexerTest extends LexerTestBase
{
    @Test
    public void testLexer() throws Exception
    {
        testLexer("AABB", "IDENT AABB\n");
        testLexer("// Kommentar\n123*", "LINECOMMENT // Kommentar\n\nINTEGER 123\nMUL *\n");
    }
}
