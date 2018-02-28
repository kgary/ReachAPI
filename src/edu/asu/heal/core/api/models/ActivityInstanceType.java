package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

public class ActivityInstanceType {
    public static String NAME_ATTRIBUTE = "name";
    public static String ACTIVITYID_ATTRIBUTE = "activityId";

    private String name;
    private ObjectId activityId;

    public ActivityInstanceType() {
    }

    public ActivityInstanceType(String name, ObjectId activityId) {
        this.name = name;
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getActivityId() {
        return activityId;
    }

    public void setActivityId(ObjectId activityId) {
        this.activityId = activityId;
    }
}
