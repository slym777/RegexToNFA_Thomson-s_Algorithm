package model;

public class State {
    private String stateId;

    private boolean isInitial;
    private boolean isFinal;

    public State(String stateId) {
        this.stateId = stateId;
        NFA.stateCount++;
    }

    public String toString() {
        return stateId;
    }

    public String getStateId() { return this.stateId; }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public void setInitial(boolean isInitial) { this.isInitial = isInitial; }

    public boolean getInitial() { return this.isInitial; }

    public void setFinal(boolean isFinal) { this.isFinal = isFinal; }

    public boolean getFinal() { return this.isFinal; }
}
