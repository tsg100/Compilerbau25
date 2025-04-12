package com.compiler.machines;

import org.junit.Test;

public class StringLiteralTest extends MachineTestBase {

    private void testWord(final String word, final Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.StringLiteral(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("\"\"", true);
        testWord("\"A\"", true);
        testWord("\"Hallo ich bin ein Test!\"", true);
        testWord("\"1\"", true);
        testWord("\"A1a2\"", true);
    }


    @Test
    public void testNoAccept() throws Exception {
        testWord("\"BB\"BBAA\"", false);
        testWord("\"BB\" 12AA\"", false);
        testWord("B\" 12AA\"", false);
        testWord("B\"", false);
        testWord("\"B", false);
        testWord("A", false);
    }
}
