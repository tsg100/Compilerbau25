package com.compiler.machines;

public class ABMachine extends com.compiler.StateMachine {

    public void initStateTable() {
        com.compiler.State startState = new com.compiler.State("start", false);
        startState.addTransition('A', "start");
        startState.addTransition('B', "expectB");
        addState(startState);
        com.compiler.State expectBState = new com.compiler.State("expectB", true);
        expectBState.addTransition('B', "expectB");
        addState(expectBState);
    }

    @Override
    public String getStartState() {
        return "start";
    }

    public com.compiler.TokenIntf.Type getType() {
        return com.compiler.TokenIntf.Type.EOF;
    }

}
