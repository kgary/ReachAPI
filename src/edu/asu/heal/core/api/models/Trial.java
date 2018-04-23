package edu.asu.heal.core.api.models;

import java.util.ArrayList;
import java.util.Date;

public class Trial implements IHealModelType{


    public static String NAME_ATTRIBUTE = "name";
    public static String TRIALID_ATTRIBUTE = "trialId";
    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String STARTDATE_ATTRIBUTE = "startDate";
    public static String ENDDATE_ATTRIBUTE = "endDate";
    public static String TARGETCOUNT_ATTRIBUTE = "targetCount";
    public static String PATIENTS_ATTRIBUTE = "patients";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";

    private String domainId;
    private String trialId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int targetCount;
    private ArrayList<String> patients = new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;

    public Trial(){
        // blank constructor
    }

    public Trial(String domainId, String trialId, String title, String description, Date startDate,
                 Date endDate, int targetCount, Date createdAt, Date updatedAt) {
        this.domainId = domainId;
        this.trialId = trialId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetCount = targetCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters and setters

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
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

    public ArrayList<String> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<String> patients) {
        this.patients = patients;
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
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
        return "Trial{" +
                ", domainId=" + domainId +
                ", trialId='" + trialId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", targetCount=" + targetCount +
                ", patients=" + patients +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.trialId == null ? 0 :this.trialId.hashCode());
        result = PRIME * result + (this.domainId == null ? 0 :this.domainId.hashCode());
        result = PRIME * result + (this.title == null ? 0 :this.title.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + this.targetCount;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof Trial)) return false;

        Trial temp = (Trial) obj;
        return this.trialId.equals(temp.trialId)
                && this.domainId.equals(temp.domainId)
                && this.title.equals(temp.title)
                && this.createdAt.equals(temp.createdAt)
                && this.targetCount == temp.targetCount;
    }
}
