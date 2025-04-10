package com.compiler.machines;

public class KeywordMachine extends com.compiler.StateMachine {
    private String keyword;
    private com.compiler.TokenIntf.Type tokenType;

    public KeywordMachine(String keyword, com.compiler.TokenIntf.Type tokenType) {
        this.keyword = keyword;
        this.tokenType = tokenType;
    }

    public void initStateTable() {
        com.compiler.State startState = new com.compiler.State("0", false);
        startState.addTransition(this.keyword.charAt(0), "1");
        addState(startState);
        for (int i = 1; i < this.keyword.length(); i++) {
            com.compiler.State state = new com.compiler.State(String.valueOf(i), false);
            state.addTransition(this.keyword.charAt(i), String.valueOf(i + 1));
            addState(state);
        }
        com.compiler.State endState = new com.compiler.State(String.valueOf(this.keyword.length()), true);
        addState(endState);
    }

    @Override
    public String getStartState() {
        return "0";
    }

    public com.compiler.TokenIntf.Type getType() {
        return tokenType;
    }

}
