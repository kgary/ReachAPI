package edu.asu.heal.core.api.models;

import java.util.ArrayList;
import java.util.Date;

public class Patient implements IHealModelType {

    public static String PIN_ATTRIBUTE = "pin";
    public static String PATIENTID_ATTRIBUTE = "patientId";
    public static String STARTDATE_ATTRIBUTE = "startDate";
    public static String ENDDATE_ATTRIBUTE = "endDate";
    public static String STATE_ATTRIBUTE = "state";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String ACTIVITYINSTANCES_ATTRIBUTE = "activityInstances";

    private String patientId;
    private int pin;
    private Date startDate;
    private Date endDate;
    private String state;
    private Date createdAt;
    private Date updatedAt;
    private ArrayList<String> activityInstances = new ArrayList<>();

    public Patient(){
        // blank constructor
    }

    public Patient(String patientId, int pin, Date startDate, Date endDate, String state, Date createdAt, Date updatedAt, ArrayList<String> activityInstances) {
        this.patientId = patientId;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public ArrayList<String> getActivityInstances() {
        return activityInstances;
    }

    public void setActivityInstances(ArrayList<String> activityInstances) {
        this.activityInstances = activityInstances;
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
        return "Patient{" +
                ", pin=" + pin +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", activityInstances=" + activityInstances +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.patientId == null ? 0 :this.patientId.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + (this.startDate == null ? 0 : this.startDate.hashCode());
        result = PRIME * result + this.pin;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof Patient)) return false;

        Patient temp = (Patient) obj;
        return this.patientId.equals(temp.patientId)
                && this.createdAt.equals(temp.createdAt)
                && this.pin == temp.pin
                && this.startDate.equals(temp.startDate);

    }


}
