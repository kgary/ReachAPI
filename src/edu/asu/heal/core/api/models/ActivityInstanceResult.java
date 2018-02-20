package edu.asu.heal.core.api.models;

import java.util.Date;

public class ActivityInstanceResult {

    private Date userSubmissionTime;
    private Date actualSubmissionTime;

    public ActivityInstanceResult(Date userSubmissionTime, Date actualSubmissionTime){
        this.userSubmissionTime = userSubmissionTime;
        this.actualSubmissionTime = actualSubmissionTime;
    }

    // getters and setters

    public Date getUserSubmissionTime() {
        return userSubmissionTime;
    }

    public void setUserSubmissionTime(Date userSubmissionTime) {
        this.userSubmissionTime = userSubmissionTime;
    }

    public Date getActualSubmissionTime() {
        return actualSubmissionTime;
    }

    public void setActualSubmissionTime(Date actualSubmissionTime) {
        this.actualSubmissionTime = actualSubmissionTime;
    }
}
