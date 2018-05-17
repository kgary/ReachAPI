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
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.activityId == null ? 0 :this.activityId.hashCode());
        result = PRIME * result + (this.name == null ? 0 :this.name.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof ActivityInstanceType)) return false;

        ActivityInstanceType temp = (ActivityInstanceType) obj;
        return this.activityId.equals(temp.activityId)
                && this.name.equals(temp.name);
    }

    @Override
    public String toString() {
        return "ActivityInstanceType{" +
                "name='" + name + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
