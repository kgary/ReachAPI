package edu.asu.heal.core.api.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class ActivityInstance {
    public static String ID_ATTRIBUTE = "_id";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String STARTTIME_ATTRIBUTE = "startTime";
    public static String ENDTIME_ATTRIBUTE = "endTime";
    public static String USERSUBMISSIONTIME_ATTRIBUTE = "userSubmissionTime";
    public static String ACTUALSUBMISSIONTIME_ATTRIBUTE = "actualSubmissionTime";
    public static String INSTANCEOF_ATTRIBUTE = "instanceOf";
    public static String STATE_ATTRIBUTE = "state";
    public static String DESCRIPTION_ATTRIBUTE = "description";


    private ObjectId id;
    private Date createdAt;
    private Date updatedAt;
    private Date startTime;
    private Date endTime;
    private Date userSubmissionTime;
    private Date actualSubmissionTime;
    private ActivityInstanceType instanceOf;
    private String state;
    private String description;

    public ActivityInstance() {
    }

    public ActivityInstance(ObjectId id, Date createdAt, Date updatedAt, Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime, ActivityInstanceType instanceOf, String state, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userSubmissionTime = userSubmissionTime;
        this.actualSubmissionTime = actualSubmissionTime;
        this.instanceOf = instanceOf;
        this.state = state;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

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

    public ActivityInstanceType getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(ActivityInstanceType instanceOf) {
        this.instanceOf = instanceOf;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
