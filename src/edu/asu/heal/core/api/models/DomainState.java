package edu.asu.heal.core.api.models;

public enum DomainState {
    CREATED("created"),
    AVAILABLE("available"),
    IN_EXECUTION("in-execution"),
    SUSPENDED("suspended"),
    COMPLETED("completed"),
    ABORTED("aborted"),
    DEACTIVATED("de-activated"),
    UNKNOWN("");

    private String state;

    DomainState(String state){
        this.state = state;
    }

    public String state(){
        return state;
    }
}
