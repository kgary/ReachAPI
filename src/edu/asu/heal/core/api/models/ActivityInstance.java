package edu.asu.heal.core.api.models;

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
    public static String ACTIVITYINSTANCEID_ATTRIBUTE = "activityInstanceId";


    private ObjectId id;
    private String activityInstanceId;
    private Date createdAt;
    private Date updatedAt;
    private String description;
    private Date startTime;
    private Date endTime;
    private Date userSubmissionTime;
    private Date actualSubmissionTime;
    private ActivityInstanceType instanceOf;
    private String state;

    private static ActivityInstance NULL_ACTIVITY_INSTANCE;

    static {
        ActivityInstance instance = new ActivityInstance();
        instance.setActivityInstanceId("NULL");
        instance.setCreatedAt(new Date(0));
        instance.setUpdatedAt(new Date(0));
        instance.setStartTime(new Date(0));
        instance.setEndTime(new Date(0));
        instance.setUserSubmissionTime(new Date(0));
        instance.setActualSubmissionTime(new Date(0));
        instance.setInstanceOf(new ActivityInstanceType("NULL", "NULL"));
        instance.setState("NULL");
        instance.setDescription("NULL");

        NULL_ACTIVITY_INSTANCE = instance;
        instance = null;
    }


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

    public String getActivityInstanceId() {
        return activityInstanceId;
    }

    public static ActivityInstance getNullActivityInstance() {
        return NULL_ACTIVITY_INSTANCE;
    }

    public static void setNullActivityInstance(ActivityInstance nullActivityInstance) {
        NULL_ACTIVITY_INSTANCE = nullActivityInstance;
    }

    public void setActivityInstanceId(String activityInstanceId) {
        this.activityInstanceId = activityInstanceId;
    }

    @Override
    public String toString() {
        return "ActivityInstance{" +
                "id=" + id +
                ", activityInstanceId='" + activityInstanceId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userSubmissionTime=" + userSubmissionTime +
                ", actualSubmissionTime=" + actualSubmissionTime +
                ", instanceOf=" + instanceOf +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.activityInstanceId == null ? 0 :this.activityInstanceId.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + (this.updatedAt == null ? 0 : this.updatedAt.hashCode());
        result = PRIME * result + (this.description == null ? 0 : this.description.hashCode());
        result = PRIME * result + (this.startTime == null ? 0 :this.startTime.hashCode());
        result = PRIME * result + (this.endTime == null ? 0 :this.endTime.hashCode());
        result = PRIME * result + (this.userSubmissionTime == null ? 0 :this.userSubmissionTime.hashCode());
        result = PRIME * result + (this.actualSubmissionTime == null ? 0 :this.actualSubmissionTime.hashCode());
        result = PRIME * result + (this.instanceOf == null ? 0 :this.instanceOf.hashCode());
        result = PRIME * result + (this.state == null ? 0 :this.state.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof ActivityInstance)) return false;

        ActivityInstance temp = (ActivityInstance) obj;
        return this.activityInstanceId.equals(temp.activityInstanceId)
                && this.createdAt.equals(temp.createdAt)
                && this.updatedAt.equals(temp.updatedAt)
                && this.description.equals(temp.description)
                && this.startTime.equals(temp.startTime)
                && this.endTime.equals(temp.endTime)
                && this.userSubmissionTime.equals(temp.userSubmissionTime)
                && this.actualSubmissionTime.equals(temp.actualSubmissionTime)
                && this.instanceOf.equals(temp.instanceOf)
                && this.state.equals(temp.state);
    }

}
