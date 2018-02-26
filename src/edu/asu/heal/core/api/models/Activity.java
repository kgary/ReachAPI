package edu.asu.heal.core.api.models;

public class Activity {

    private String title;
    private String description;

    public Activity(){
        // blank constructor
    }

    public Activity(String title, String description){
        this.title = title;
        this.description = description;
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

}