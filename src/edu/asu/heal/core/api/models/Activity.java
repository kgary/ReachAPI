package edu.asu.heal.core.api.models;

import java.util.Date;

public class Activity implements IHealModelType{

    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String ACTIVITYID_ATTRIBUTE = "activityId";

    private String activityId;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public Activity(){
        // blank constructor
    }

    public Activity(String activityId, String title, String description, Date createdAt, Date updatedAt) {
        this.activityId = activityId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.activityId == null ? 0 :this.activityId.hashCode());
        result = PRIME * result + (this.title == null ? 0 :this.title.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + (this.updatedAt == null ? 0 : this.updatedAt.hashCode());
        result = PRIME * result + (this.description == null ? 0 : this.description.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof Activity)) return false;

        Activity temp = (Activity) obj;
        return this.activityId.equals(temp.activityId)
                && this.title.equals(temp.title)
                && this.createdAt.equals(temp.createdAt)
                && this.updatedAt.equals(temp.updatedAt)
                && this.description.equals(temp.description);
    }

    @Override
    public String toString() {
        return "Activity{" +
                ", activityId='" + activityId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
