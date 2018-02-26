package edu.asu.heal.core.api.models;

public enum ActivityInstanceStatus {

    CREATED("created"),
    AVAILABLE("available"),
    IN_EXECUTION("in-execution"),
    SUSPENDED("suspended"),
    COMPLETED("completed"),
    ABORTED("aborted"),
    DEACTIVATED("de-activated"),
    UNKNOWN("");

    private String status;

    ActivityInstanceStatus(String status){
        this.status = status;
    }

    public String status(){
        return status;
    }
}
