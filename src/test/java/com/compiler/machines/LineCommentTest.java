package com.compiler.machines;

import com.compiler.machines.LineCommentMachine;
import org.junit.Test;

public class LineCommentTest  extends MachineTestBase{

    private void testWord(String word, Boolean expectOk) throws Exception {
        testWord(new LineCommentMachine(), word, expectOk);
    }

    @Test
    public void testAcceptedWord() throws Exception {
        testWord("// das ist ein test\n", true);
        testWord("//test123\n", true);
        testWord("//                 whitespace t0wn\n", true);
        testWord("//50nd3rz31ch3n\n", true);
        testWord("// 50|\\\\||)3|27_3!(|-|3|\\\\|\n", true); // 'Sonderzeichen' in advanced leadspeak
    }

    @Test
    public void testUnacceptedWord() throws Exception {
        testWord("Mein Kommentar", false);
        testWord("11833 -  Die Auskunft der...", false);
        testWord("//This line has no end", false);
        testWord("\n", false);
        testWord("/kommentar\n", false);

    }


}
