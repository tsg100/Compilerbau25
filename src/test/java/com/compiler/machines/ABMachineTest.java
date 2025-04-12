package com.compiler.machines;

import org.junit.Test;

public class ABMachineTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.ABMachine(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("AB", true);
        testWord("AABBBB", true);
        testWord("ABB", true);
        testWord("B", true);
    }

    @Test
    public void testNoAccept() throws Exception {
        testWord("BBBBAA", false);
        testWord("A", false);
    }
}
