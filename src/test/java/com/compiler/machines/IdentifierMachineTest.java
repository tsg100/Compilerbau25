package com.compiler.machines;

import org.junit.Test;

import com.MachineTestBase;

public class IdentifierMachineTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.IdentifierMachine(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("aB5", true);
        testWord("_2cc", true);
        testWord("__", true);
        testWord("_", true);
        testWord("A", true);
    }

    @Test
    public void testNoAccept() throws Exception {
        testWord("5", false);
        testWord("5_", false);
        testWord("10_", false);
    }
}