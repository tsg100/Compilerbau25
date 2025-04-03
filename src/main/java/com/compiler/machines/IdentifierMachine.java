package com.compiler.machines;

public class IdentifierMachine extends com.compiler.StateMachine{
    
    public void initStateTable() {
        com.compiler.State firstCharacter = new com.compiler.State("firstCharacter", false);
        addState(firstCharacter);

        com.compiler.State followingCharacters = new com.compiler.State("followingCharacter", true);
        addState(followingCharacters);

        firstCharacter.addTransitionRange('a', 'z', followingCharacters.getName());
        firstCharacter.addTransitionRange('A', 'Z', followingCharacters.getName());
        firstCharacter.addTransition('_', followingCharacters.getName());
        addUmlautsTransition(firstCharacter, followingCharacters.getName());

        followingCharacters.addTransition('_', followingCharacters.getName());
        followingCharacters.addTransitionRange('a', 'z', followingCharacters.getName());
        followingCharacters.addTransitionRange('A', 'Z', followingCharacters.getName());
        followingCharacters.addTransitionRange('0', '9', followingCharacters.getName());
        addUmlautsTransition(followingCharacters, followingCharacters.getName());
    }

    private void addUmlautsTransition(com.compiler.State sourceState, String targetState){
        sourceState.addTransition('Ä', targetState);
        sourceState.addTransition('ä', targetState);

        sourceState.addTransition('Ö', targetState);
        sourceState.addTransition('ö', targetState);

        sourceState.addTransition('Ü', targetState);
        sourceState.addTransition('ü', targetState);

        sourceState.addTransition('ß', targetState);
    }

    @Override
    public String getStartState() {
        return "firstCharacter";
    }

    public com.compiler.TokenIntf.Type getType() {
        return com.compiler.TokenIntf.Type.IDENT;
    }

}
