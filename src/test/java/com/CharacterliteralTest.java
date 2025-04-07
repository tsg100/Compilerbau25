package com;

import static org.junit.Assert.*;

import org.junit.Test;

import com.compiler.machines.Characterliteral;

import org.junit.Test;

public class CharacterliteralTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new Characterliteral(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("'a'", true);
        testWord("'\\'", true);
        testWord("'0'", true);

}

    @Test
    public void testNoAccept() throws Exception {
        testWord("'''", false);
        testWord("'ab'", false);
    }
}
