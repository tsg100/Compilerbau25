package com;

import static org.junit.Assert.assertTrue;

public class MachineTestBase {
    protected void testWord(com.compiler.StateMachineIntf machine, String word, Boolean expectOk) throws Exception {
        java.io.OutputStreamWriter outWriter = new java.io.OutputStreamWriter(System.out);
        machine.process(word, outWriter);
        assertTrue(machine.isFinalState() == expectOk);
    }

}
