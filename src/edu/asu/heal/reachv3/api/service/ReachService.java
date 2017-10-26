package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.model.ScheduleModel;

import java.util.Random;

public class ReachService implements IHealContract {

    @Override
    public String getActivityInstances() {
        try {
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

    @Override
    public String scheduleActivityInstancesForPatient(String requestBody){
        try {
            //Mock data as of now
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestBody, ScheduleModel.class);

            return model.toString();
        }catch (Exception e){
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }

        return null;
    }

    // patient resource method
    @Override
    public String getPatients(){
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

    /*@Override
    public String getPatientActivities(String patientPin) {
        return "Patient Activities: "+patientPin;
    }

    @Override
    public String schedulePatientActivities(String patientPin, String requestBody) {
        return "Schedule Patient Activities: "+ patientPin;
    }*/
}