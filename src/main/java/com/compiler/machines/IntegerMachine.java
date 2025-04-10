package com.compiler.machines;

import com.compiler.State;
import com.compiler.StateMachine;
import com.compiler.TokenIntf.Type;

public class IntegerMachine extends StateMachine {

    public void initStateTable() {
        State startState = new State("start", false);
        State numberState = new State("numbers", true);
        startState.addTransition('0', "zero");
        startState.addTransitionRange('1', '9', "numbers");
        numberState.addTransitionRange('0', '9', "numbers");

        addState(startState);
        addState(new State("zero", true));
        addState(numberState);
    }

    @Override
    public String getStartState() {
        return "start";
    }

    public Type getType() {
        return Type.INTEGER;
    }

}
