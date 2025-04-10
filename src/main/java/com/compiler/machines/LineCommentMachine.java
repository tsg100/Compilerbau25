package com.compiler.machines;

import com.compiler.State;
import com.compiler.StateMachine;
import com.compiler.TokenIntf.Type;

public class LineCommentMachine extends StateMachine {

    @Override
    public void initStateTable() {
        State firstCommentSlashState = new State("firstSlash", false);
        firstCommentSlashState.addTransition('/', "secondSlash");
        addState(firstCommentSlashState);

        State secondCommentSlashState = new State("secondSlash", false);
        secondCommentSlashState.addTransition('/', "comment");
        addState(secondCommentSlashState);

        //we are now in the comment - allow any character
        //also allow whitespace after //
        //go over to end state when \n is read
        State commentState = new State("comment", false);
        commentState.addTransitionRange(' ', '~', "comment");
        //german special characters (e.g. ä, ö, ü, ...) are excluded (because im too lazy) D:<

        commentState.addTransition('\n', "end");
        addState(commentState);

        State endState = new State("end", true);
        addState(endState);

    }

    @Override
    public String getStartState() {
        return "firstSlash";
    }

    @Override
    public Type getType() {
        return Type.LINECOMMENT;
    }
}
