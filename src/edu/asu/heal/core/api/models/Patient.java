package edu.asu.heal.core.api.models;

import java.util.ArrayList;
import java.util.Date;

public class Patient {

    private int pin;
    private Date startDate;
    private Date endDate;
    private String state;
    private ArrayList<ActivityInstance> activityInstances = new ArrayList<ActivityInstance>();

    public Patient(){
        // blank constructor
    }

    public Patient(int pin, Date startDate, Date endDate, String state, ArrayList<ActivityInstance> activityInstances){
        this.pin = pin;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
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

    public ArrayList<ActivityInstance> getActivityInstances() {
        return activityInstances;
    }

    public void setActivityInstances(ArrayList<ActivityInstance> activityInstances) {
        this.activityInstances = activityInstances;
    }

}
