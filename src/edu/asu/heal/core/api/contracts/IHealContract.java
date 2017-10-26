package edu.asu.heal.core.api.contracts;

public interface IHealContract {

    // methods pertaining to activityInstance resource

    String getActivityInstances(String patientPin, int trialId);

    String getActivityInstance(String activityInstanceId);

    String createActivityInstance(String requestPayload);

    // TODO -- discuss with team about a probable confusion -- @author dpurbey
    /*
    * confusion - whether there should be individual attribute's update method or not
    * if not then the sql statement to perform update operation for certain set of attribute would require if/else
    * blocks and sql statement would be constructed in the service rather than picking up from the dao.properties
    * */
    String updateActivityInstance(String requestBody);

    String deleteActivityInstance(String activityInstanceId);

    // methods pertaining to activity resource
    String createActivity(String requestBody);

    // methods pertaining to patient resource
    String getPatients(int trialId);

    String getPatient(String patientPin);

    String createPatient(String requestBody);

    String updatePatient(String requestBody);

    String deletePatient(String patientPin);

}