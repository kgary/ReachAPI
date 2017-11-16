package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.model.ScheduleModel;

public class ReachService implements HealService {

    @Override
    public String getActivityInstances(String patientPin, int trialId) {
        try {

            // TODO -- scope in the possibility that when queryParams(patientPin, trialId) are not present, then
            // we will return activityInstance collection

            // return the mockup data
            DAO dao = DAOFactory.getTheDAO();
            ScheduleModel instance = (ScheduleModel)dao.getScheduledActivities( 2);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(instance);
        } catch(Exception e){
            //TODO String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Invalid survey instance ID"));
            //TODO throw new NotFoundException(Response.Status.BAD_REQUEST, JsonErrorMessage);

            System.out.println("Do something here");
        }
        return null;
    }

    @Override
    public String getActivityInstance(String activityInstanceId){
        return "ActivityInstance: "+activityInstanceId;
    }

    @Override
    public String createActivityInstance(String requestPayload) {
        try {
            //Mock data as of now
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestPayload, ScheduleModel.class);

            System.out.println(model.toString());

            return "OK";
        }catch (Exception e){
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateActivityInstance(String requestBody) {
        return "UPDATE AI";
    }

    @Override
    public String deleteActivityInstance(String activityInstanceId){
        return "DELETE AI: "+activityInstanceId;
    }

    // methods pertaining to activity resource
    @Override
    public String createActivity(String requestBody){
        return "Create activity for patient";
    }

    // patient resource method
    @Override
    public String getPatients(int trialId){
        // explore the option - if trialId is not present then return patients collections
        return "GET ALL PATIENTS";
    }

    @Override
    public String getPatient(String patientPin) {
        return "GET PATIENT: " + patientPin;
    }

    @Override
    public String createPatient(String requestBody) {
        return null;
    }

    @Override
    public String updatePatient(String requestBody) {
        return null;
    }

    @Override
    public String deletePatient(String patientPin){
        return "DELETE PATIENT";
    }

}