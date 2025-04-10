package com.compiler.machines;

import com.compiler.State;
import com.compiler.StateMachine;
import com.compiler.TokenIntf;

import java.util.List;

public class BlockCommentMachine extends StateMachine {
    List<Character> everything = List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '_', '-', '+', '=', ':', ';', '!', '@', '#', '$', '%', '^', '&', '(', ')', '{', '}', '[', ']', '|',
            '\\', '\'', '"', '<', '>', ',', '.', '?', '`', '~', ' ', '\t', '\n', '\r', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    @Override
    public void initStateTable() {
        State startState = new com.compiler.State("S", false);
        startState.addTransition('/', "1");
        addState(startState);

        State state1 = new com.compiler.State("1", false);
        state1.addTransition('*', "3");
        addState(state1);

        State state3 = new com.compiler.State("3", false);
        everything.stream().filter(c -> c != '*').forEach(c -> state3.addTransition(c, "3"));
        state3.addTransition('*', "4");
        addState(state3);

        State state4 = new com.compiler.State("4", false);
        everything.stream().filter(c -> c != '*' && c != '/').forEach(c -> state3.addTransition(c, "3"));
        state4.addTransition('*', "4");
        state4.addTransition('/', "E");
        addState(state4);

        State end = new com.compiler.State("E", true);
        addState(end);
    }

    @Override
    public String getStartState() {
        return "S";
    }

    @Override
    public TokenIntf.Type getType() {
        return com.compiler.TokenIntf.Type.MULTILINECOMMENT;
    }
}
