package com.compiler.machines;

import com.compiler.TokenIntf;

public class StringLiteral extends com.compiler.StateMachine {

    @Override
    public void initStateTable() {
        final com.compiler.State startState = new com.compiler.State("start", false);
        startState.addTransition('"', "state1");
        addState(startState);

        final com.compiler.State state1 = new com.compiler.State("state1", false);
        // Wir haben in der ASCII-Tabelle geschaut und erlauben alle Zeichen von
        // SPACE (32) bis " (34) und dann von " (34) bis ~ (126).
        // In diesem Bereich befinden sich alle Buchstaben, Zahlen und Sonderzeichen von ASCII.
        // Umlaute oder z. B. UTF-8 spezifische Zeichen sind nicht erlaubt!!
        state1.addTransitionRange(' ', (char) ('"' - 1), "state1");
        state1.addTransitionRange((char) ('"' + 1), '~', "state1");
        state1.addTransition('"', "end");
        addState(state1);

        final com.compiler.State endState = new com.compiler.State("end", true);
        addState(endState);
    }


    @Override
    public String getStartState() {
        return "start";
    }


    @Override
    public TokenIntf.Type getType() {
        return TokenIntf.Type.STRING;
    }
}
