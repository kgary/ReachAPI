package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

import java.util.Date;

public class Activity {

    public static String ID_ATTRIBUTE = "_id";
    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";

    private ObjectId id;
    private String title;
    private String description;
    private Date createdAt; // TODO Shall we add this too @dpurbey?
    private Date updatedAt; // TODO Shall we add this too @dpurbey?

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

}
