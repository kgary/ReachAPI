package edu.asu.heal.core.api.models;

public class ActivityInstanceType {
    public static String NAME_ATTRIBUTE = "name";
    public static String ACTIVITYID_ATTRIBUTE = "activityId";

    private String name;
    private String activityId;

    public ActivityInstanceType() {
    }

    public ActivityInstanceType(String name, String activityId) {
        this.name = name;
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "ActivityInstanceType{" +
                "name='" + name + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
