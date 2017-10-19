package edu.asu.heal.core;

import java.util.Date;

public interface HealCoreInterface {

    // get all activityInstances associated with a patient
    String getActivityInstances(String patientPin);

    // get the details of individual activity instance of particular patient
    String getActivityInstanceDetail(Integer activityInstanceId, String patientPin);

    // create activity instance for a patient
    /*
    * String createActivityInstance(Date startTime, Date endTime, Date userSubmissionType, Date actualSubmissionType,
    *                              String State, String patientPin, String Sequence, String activityTitle,
    *                              String description);
    *
    *                              Commented by Mohit and replaced with the method below
    */

    String createActivityInstance(String requestPayload);


    // TODO -- discuss with team about a probable confusion
    /*
    * confusion - whether there should be individual attribute's update method or not
    * if not then the sql statement to perform update operation for certain set of attribute would require if/else
    * blocks and sql statement would be constructed in the service rather than picking up from the dao.properties
    * */
    String updateActivities(String patientPin);
}