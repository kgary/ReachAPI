package edu.asu.heal.core.api.contracts;

public interface IHealContract {

    // methods pertaining to activityInstance resource
    // get all activityInstances associated with a patient
    String getActivityInstances();

    String getActivityInstance(String activityInstanceId);

    // create activity instance
    String createActivityInstance(String requestPayload);


    // TODO -- discuss with team about a probable confusion -- @author dpurbey
    /*
    * confusion - whether there should be individual attribute's update method or not
    * if not then the sql statement to perform update operation for certain set of attribute would require if/else
    * blocks and sql statement would be constructed in the service rather than picking up from the dao.properties
    * */
    String updateActivityInstance(String requestBody);

    String deleteActivityInstance(String activityInstanceId);

    String scheduleActivityInstancesForPatient(String requestBody);

    // methods pertaining to patient resource
    String getPatients();

    String getPatient(String patientPin);

    String createPatient(String requestBody);

    String updatePatient(String requestBody);

    String deletePatient(String patientPin);

    /*String getPatientActivities(String patientPin);

    String schedulePatientActivities(String patientPin, String requestBody);*/

}