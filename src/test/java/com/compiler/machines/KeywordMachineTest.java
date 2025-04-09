package com.compiler.machines;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.MachineTestBase;

public class KeywordMachineTest extends MachineTestBase {

	private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new com.compiler.machines.KeywordMachine(), word, expectOk);
    }

	List<String> keywords = List.of(
    		"if",
    		"else",
    		"while",
    		"do",
    		"for",
    		"loop",
    		"endloop",
    		"break",
    		"switch",
    		"case",
    		"execute",
    		"times",
    		"function",
    		"call",
    		"return",
    		"block",
    		"default");
	
    @Test
    public void testAccept() throws Exception {
        for (String keyword : keywords) {
			testWord(keyword, true);
		}
    }

    @Test
    public void testNoAccept() throws Exception {
    	testWord("IF", false);
        testWord("an", false);
        testWord("o", false);
        testWord("retur", false);
        testWord("__fsdf", false);
        testWord("lovis", false);
        testWord("omit", false);
        testWord("defaulte", false);
        testWord("elif", false);
    }
    
}
