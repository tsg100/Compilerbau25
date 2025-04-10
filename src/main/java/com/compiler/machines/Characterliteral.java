package com.compiler.machines;

import java.util.List;

import com.compiler.State;
import com.compiler.StateMachine;
import com.compiler.TokenIntf.Type;

public class Characterliteral extends StateMachine {

	@Override
	public void initStateTable() {
		List<Character> everything = List.of( '_', '-', '+', '=', ':', ';', '!', '@', '#', '$', '%', '^', '&', '(', ')', '{', '}', '[', ']', '|',
	            '\\', '"', '<', '>', ',', '.', '?', '`', '~', ' ');
		
        com.compiler.State startState = new com.compiler.State("start", false);
        startState.addTransition('\'', "BeginnState");
        addState(startState);
        
        State beginnState = new State("BeginnState", false);
        beginnState.addTransitionRange('a', 'z', "CharState");
        beginnState.addTransitionRange('A', 'Z', "CharState");
        beginnState.addTransitionRange('0', '9', "CharState");
        everything.stream().forEach(c -> beginnState.addTransition(c, "CharState"));
        addState(beginnState);
        
        State charState = new State("CharState", false);
        charState.addTransition('\'', "EndState");
        addState(charState);
        
        State endState = new State("EndState", true);
        addState(endState);
	}

	@Override
	public String getStartState() {
		return "start";
	}

	@Override
	public Type getType() {
		return com.compiler.TokenIntf.Type.CHAR;
	}

}
