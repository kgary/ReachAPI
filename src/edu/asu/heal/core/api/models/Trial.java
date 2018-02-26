package edu.asu.heal.core.api.models;

import java.util.ArrayList;
import java.util.Date;

public class Trial {


    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int targetCount;
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public Trial(){
        // blank constructor
    }

    public Trial(String title, String description, Date startDate, Date endDate, int targetCount,
                 ArrayList<Patient> patients){

        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetCount = targetCount;
        this.patients = patients;
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

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

}
