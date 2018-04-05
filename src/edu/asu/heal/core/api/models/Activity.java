package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

import java.util.Date;

public class Activity {

    public static String ID_ATTRIBUTE = "_id";
    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String ACTIVITYID_ATTRIBUTE = "activityId";

    private ObjectId id;
    private String activityId;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public Activity(){
        // blank constructor
    }

    public Activity(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Activity(ObjectId id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // getters and setters

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityId='" + activityId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
