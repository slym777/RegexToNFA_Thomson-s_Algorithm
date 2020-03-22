package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NFA {
    private String regex; // the regex in infix
    private List<Character> symbolList; // NFA's symbol list
    private List<Transition> transitionsList; // NFA's transitions list
    private List<State> finalStates; // NFA's final states list
    private State initialState; // NFA's initial state
    private List<State> states; // NFA's states

    public static int stateCount; // id for States

    private State saveFinal; // used to store final state during concatenation
    private Stack<State> stackFromState;
    private Stack<State> stackToState;

    public NFA(){
    }
    
    public NFA(String regex, boolean isInfix) {
        stateCount = 0;
        this.regex = regex;
        if (isInfix) {
            this.regex = PostFix.infixToPostfix(regex);
        }
        symbolList = new LinkedList<Character>();
        transitionsList = new LinkedList<Transition>();
        finalStates = new LinkedList<State>();
        states = new LinkedList<State>();
        stackFromState = new Stack<>();
        stackToState = new Stack<>();
        computeSymbolList();
        regExpToAFN();
        computeStateList();
    }

    /** checks for symbols in postFixRegExp and adds them to the list of symbols */
    private void computeSymbolList() {
        for (int i = 0; i < regex.length(); i++) {
            if(!PostFix.precedenceMap.containsKey(regex.charAt(i)))
                if (!symbolList.contains(regex.charAt(i)))
                    symbolList.add(regex.charAt(i));
        }
        Collections.sort(symbolList);
    }

    private void computeStateList() {

        for (Transition transition : transitionsList) {
            if (!states.contains(transition.getFromState())) {
                states.add(transition.getFromState());
            }

            if (!states.contains(transition.getToState())) {
                states.add(transition.getToState());
            }
        }
        states.remove(initialState);
        initialState.setStateId("S" + initialState);

        for(State state : states){
            if (finalStates.contains(state)){
                state.setStateId("F" + state.getStateId());
            }
            else {
                state.setStateId("Q" + state.getStateId());
            }
        }

        states.add(initialState);
    }

    /** construct the NFA */
    private void regExpToAFN(){
        for (int i = 0; i < regex.length(); i ++) {
            // if next symbol is not operator, construct a simple transition
            if (symbolList.contains(regex.charAt(i))) {
                Transition tr1 = new Transition(Character.toString(regex.charAt(i)));
                transitionsList.add(tr1);
                State initialState = tr1.getFromState();
                State finalState = tr1.getToState();
                stackFromState.push(initialState);
                stackToState.push(finalState);

            } else if (Character.toString(regex.charAt(i)).equals("|")) {
                State lowerInitial = stackFromState.pop();
                State lowerFinal = stackToState.pop();
                State upperInitial = stackFromState.pop();
                State upperFinal = stackToState.pop();
                union(upperInitial, upperFinal, lowerInitial, lowerFinal);

            } else if (Character.toString(regex.charAt(i)).equals("*")) {
                State initialState = stackFromState.pop();
                State finalState = stackToState.pop();
                kleene(initialState, finalState);

            } else if (Character.toString(regex.charAt(i)).equals(".")) {
                saveFinal = stackToState.pop();
                State finalState = stackToState.pop();
                State initialState = stackFromState.pop();
                concatenate(finalState, initialState);
            }

            if (i == regex.length()-1) {
                finalStates.add(stackToState.pop());
                if (symbolList.contains('λ')) {
                    symbolList.remove('λ');
                }
            }
        }

        // initial state
        initialState = stackFromState.pop();
    }

    /** union according Thompson's algorithm */
    private void union(State upperInitialState, State upperFinalState, State lowerInitialState, State lowerFinalState) {
        State in = new State(String.valueOf(stateCount));
        State out = new State(String.valueOf(stateCount));

        Transition tr1 = new Transition("λ", in, upperInitialState);
        Transition tr2 = new Transition("λ", in, lowerInitialState);
        Transition tr3 = new Transition("λ", upperFinalState, out);
        Transition tr4 = new Transition("λ", lowerFinalState, out);

        transitionsList.add(tr1);
        transitionsList.add(tr2);
        transitionsList.add(tr3);
        transitionsList.add(tr4);

        stackFromState.push(in);
        stackToState.push(out);
    }

    /** concatenation according Thompson's algorithm */
    private void concatenate(State initialState, State finalState) {
        Transition tr1 = new Transition("λ", initialState, finalState);
        transitionsList.add(tr1);

        stackToState.push(saveFinal);
        saveFinal = null;
    }

    /** Kleene star according Thompson's algorithm */
    private void kleene(State initialState, State finalState) {
        State in = new State(String.valueOf(stateCount));
        State out = new State(String.valueOf(stateCount));

        Transition tr1 = new Transition("λ", finalState, initialState);
        Transition tr2 = new Transition("λ", in, out);
        Transition tr3 = new Transition("λ", in, initialState);
        Transition tr4 = new Transition("λ", finalState, out);

        transitionsList.add(tr1);
        transitionsList.add(tr2);
        transitionsList.add(tr3);
        transitionsList.add(tr4);

        stackFromState.push(in);
        stackToState.push(out);
    }

    public List<Character> getSymbolList () {
        return this.symbolList;
    }

    public List<Transition> getTransitionsList () {
        return this.transitionsList;
    }

    public State getInitialState() {
        return initialState;
    }

    public List<State> getFinalStates () {
        return this.finalStates;
    }

    public List<State> getStates () {
        return this.states;
    }
}
