package model;public class Transition {    private State fromState;    private State toState;    private String transitionSymbol;    public Transition(String transitionSymbol) {        this.transitionSymbol = transitionSymbol;        this.fromState = new State(String.valueOf(NFA.stateCount));        this.toState = new State(String.valueOf(NFA.stateCount));    }    public Transition(String transitionSymbol, State initialState, State finalState) {        this.transitionSymbol = transitionSymbol;        this.fromState = initialState;        this.toState = finalState;    }    public State getFromState() {        return this.fromState;    }    public State getToState() {        return this.toState;    }    public String toString() {        return fromState.toString() + " - " + transitionSymbol + " - " + toState.toString();    }    public String getTransitionSymbol() { return this.transitionSymbol; }}