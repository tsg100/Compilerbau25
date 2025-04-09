package com;

import org.junit.Test;

public class IntegerMachineTest extends MachineTestBase {

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.IntegerMachine(), word, expectOk);
    }

    @Test
    public void testAccept() throws Exception {
        testWord("0", true);        
        testWord("1", true);        
        testWord("123", true);      
        testWord("987654", true);   
        testWord("42", true); 
    }

    @Test
    public void testNoAccept() throws Exception {
        testWord("00", false);      
        testWord("0123", false);    
        testWord("-5", false);      
        testWord("1a3", false);     
        testWord("", false);   
    }
}
