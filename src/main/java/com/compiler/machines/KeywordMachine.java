package com.compiler.machines;

import java.util.ArrayList;
import java.util.List;

import com.compiler.State;
import com.compiler.StateMachine;
import com.compiler.TokenIntf.Type;

public class KeywordMachine extends StateMachine {

    private List<State> stateList = new ArrayList<>();
    
    @Override
    public void initStateTable() {
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

        State startState = new State("S", false);
        stateList.add(startState); 

        addState(startState);

        keywords.stream().forEach(keyword -> addStatesForKeyword(keyword));
    }

    /**
     * Diese Methode added jeden Zustand nach den Zeichen. Bei return ist
     * es beispielsweise r,re,ret,retu,retur,return. Es geht vom vorherigen
     * Zustand aus und verlinkt es für den Neuen. Es wird bei jedem Durchgang 
     * geschaut, ob der State schon angelegt wurde in {@link #stateList}. 
     * Wenn nicht, wird es angelegt.
     * 
     * @param keyword
     */
    private void addStatesForKeyword(String keyword) {
        String currentStateName = "S";
        State currentState = findStateByName(currentStateName);
        char[] characters = keyword.toCharArray();
        
        for (int i = 0; i < characters.length; i++) {
            
        	char c = characters[i];
            String nextStateName = currentStateName.equals("S") ? String.valueOf(c) : currentStateName + c;

            State nextState = findStateByName(nextStateName);
            
            if (nextState == null) {
                boolean isFinal = (i == characters.length - 1);
                nextState = new State(nextStateName, isFinal);
                addState(nextState);
                stateList.add(nextState);
            }

            currentState.addTransition(c, nextStateName);

            currentState = nextState;
            currentStateName = nextStateName;
        
        }
    }

    private State findStateByName(String name) {
        for (State s : stateList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String getStartState() {
        return "S";
    }

    @Override
    public Type getType() {
        return Type.EOF;
    }
}
