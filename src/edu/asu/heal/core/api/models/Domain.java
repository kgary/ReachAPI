package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Domain {

    private ObjectId id;
    private String title;
    private String description;
    private String state;
    private ArrayList<Activity> activities;
    private ArrayList<Trial> trials;

    public Domain(){
        // default constructor
    }

    public Domain(String title, String description, String state){
        this.title = title;
        this.description = description;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public ArrayList<Trial> getTrials() {
        return trials;
    }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }
}
