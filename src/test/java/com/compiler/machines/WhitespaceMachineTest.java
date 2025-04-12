package com.compiler.machines;

import com.compiler.machines.WhitespaceMachine;
import org.junit.Test;

public class WhitespaceMachineTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new WhitespaceMachine(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("\r\n", true);
        testWord("\t", true);
        testWord(" ", true);
        testWord("\n\r\t \n \t\r", true);
    }

    @Test
    public void testNoAccept() throws Exception {
        testWord("A", false);
        testWord("\nA", false);
        testWord("A\t", false);
        testWord(" A", false);
    }
}
