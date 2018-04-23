package edu.asu.heal.core.api.models;

import java.util.ArrayList;
import java.util.Date;

public class Domain implements IHealModelType {

    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String STATE_ATTRIBUTE = "state";
    public static String ACTIVITIES_ATTRIBUTE = "activities";
    public static String TRIALS_ATTRIBUTE = "trials";
    public static String DOMAINID_ATTRIBUTE = "domainId";

    private String domainId;
    private String title;
    private String description;
    private String state;
    private Date createdAt;
    private Date updatedAt;
    private ArrayList<String> activities = new ArrayList<>();
    private ArrayList<String> trials = new ArrayList<>();

    public Domain(){
        // default constructor
    }

    public Domain(String title, String description, String state){
        this.title = title;
        this.description = description;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public ArrayList<String> getTrials() {
        return trials;
    }

    public void setTrials(ArrayList<String> trials) {
        this.trials = trials;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
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
        return "Domain{" +
                ", domainId='" + domainId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", activities=" + activities +
                ", trials=" + trials +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.domainId == null ? 0 :this.domainId.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + (this.title == null ? 0 : this.title.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof Domain)) return false;

        Domain temp = (Domain) obj;
        return this.domainId.equals(temp.domainId)
                && this.title.equals(temp.title)
                && this.createdAt.equals(temp.createdAt);

    }

}
