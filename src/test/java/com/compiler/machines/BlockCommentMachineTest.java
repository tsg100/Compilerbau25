package com.compiler.machines;

import org.junit.Test;

public class BlockCommentMachineTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.BlockCommentMachine(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("/**/", true);
        testWord("/*****/", true);
        testWord("/* */", true);
        testWord("/* akjsdha k */", true);
        testWord("""
/*
akjsdha k
*/""", true);
    }

    @Test
    public void testNoAccept() throws Exception {
        testWord("BBBBAA", false);
        testWord("A", false);
        testWord("/* */ */", false);
    }
}
