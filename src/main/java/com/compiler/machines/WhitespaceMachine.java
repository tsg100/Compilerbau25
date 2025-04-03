package com.compiler.machines;


import com.compiler.State;
import com.compiler.TokenIntf;

public class WhitespaceMachine extends com.compiler.StateMachine {

    public void initStateTable() {
        State startState = new State("start", false);
        startState.addTransition(' ', "final");
        startState.addTransition('\t', "final");
        startState.addTransition('\n', "final");
        startState.addTransition('\r', "final");
        addState(startState);
        State finalState = new State("final", true);
        finalState.addTransition(' ', "final");
        finalState.addTransition('\t', "final");
        finalState.addTransition('\n', "final");
        finalState.addTransition('\r', "final");

        addState(finalState);
    }

    @Override
    public String getStartState() {
        return "start";
    }

    public TokenIntf.Type getType() {
        return TokenIntf.Type.WHITESPACE;
    }

}
