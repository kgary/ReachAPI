package edu.asu.heal.core.api.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class Trial {


    public static String NAME_ATTRIBUTE = "name";
    public static String ID_ATTRIBUTE = "_id";
    public static String TITLE_ATTRIBUTE = "title";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String STARTDATE_ATTRIBUTE = "startDate";
    public static String ENDDATE_ATTRIBUTE = "endDate";
    public static String TARGETCOUNT_ATTRIBUTE = "targetCount";
    public static String PATIENTS_ATTRIBUTE = "patients";
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";

    private ObjectId id;
    private ObjectId domainId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int targetCount;
    private ArrayList<ObjectId> patients = new ArrayList<>();
//    private Date createdAt; TODO shall we add this field too @dpurbey?
//    private Date updatedAt;  TODO shall we add this field too @dpurbey?

    public Trial(){
        // blank constructor
    }

    public Trial(String title, String description, Date startDate, Date endDate, int targetCount,
                 ArrayList<ObjectId> patients){

        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetCount = targetCount;
        this.patients = patients;
    }

    public Trial(ObjectId domainId, String title, String description, Date startDate, Date endDate, int targetCount){
        this.domainId = domainId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetCount = targetCount;
    }

    // getters and setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getDomainId() {
        return domainId;
    }

    public void setDomainId(ObjectId domainId) {
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

    public ArrayList<ObjectId> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<ObjectId> patients) {
        this.patients = patients;
    }

}
