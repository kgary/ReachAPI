package edu.asu.heal.core.api.models;

import java.util.Date;

public class ActivityInstance {

    private String title;
    private Date startTime;
    private Date endTime;
    private String sequence;
    private ActivityInstanceStatus status;
    private ActivityInstanceResult result;

    public ActivityInstance(){
        // blank constructor
    }

    public ActivityInstance(String title, Date startTime, Date endTime, String sequence,
                            ActivityInstanceStatus status, ActivityInstanceResult result){

        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sequence = sequence;
        this.status = status;
        this.result = result;
    }

    // getters and setters
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ActivityInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityInstanceStatus status) {
        this.status = status;
    }

    public ActivityInstanceResult getResult() {
        return result;
    }

    public void setResult(ActivityInstanceResult result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
