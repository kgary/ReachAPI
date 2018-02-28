package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class Patient {

    public static String ID_ATTRIBUTE = "_id";
    public static String PIN_ATTRIBUTE = "pin";
    public static String STARTDATE_ATTRIBUTE = "startDate";
    public static String ENDDATE_ATTRIBUTE = "endDate";
    public static String STATE_ATTRIBUTE = "state";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String ACTIVITYINSTANCES_ATTRIBUTE = "activityInstances";

    private ObjectId id;
    private int pin;
    private Date startDate;
    private Date endDate;
    private String state;
    private Date createdAt;
    private Date updatedAt;
    private ArrayList<ObjectId> activityInstances = new ArrayList<ObjectId>();

    public Patient(){
        // blank constructor
    }

    public Patient(ObjectId id, int pin, Date startDate, Date endDate, String state, Date createdAt, Date updatedAt, ArrayList<ObjectId> activityInstances) {
        this.id = id;
        this.pin = pin;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.activityInstances = activityInstances;
    }

    // Getters and Setters
    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<ObjectId> getActivityInstances() {
        return activityInstances;
    }

    public void setActivityInstances(ArrayList<ObjectId> activityInstances) {
        this.activityInstances = activityInstances;
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
}
