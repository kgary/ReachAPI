package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.models.Trial;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReachService implements HealService {

    @Override
    public String addDomain(String title, String description, String state) {

        try {
            DAO dao = DAOFactory.getTheDAO();
            Domain instance = new Domain(title, description, state);
            instance.setActivities(new ArrayList<Activity>());
            instance.setTrials(new ArrayList<Trial>());

            return dao.createDomain(instance);
        } catch (Exception e){
            return e.getMessage();
        }
    }

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
            DAO dao = DAOFactory.getTheDAO();
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestPayload, ScheduleModel.class);

            int day = model.getDay();
            boolean COMPLETED = false;

            if(model.isSafe()){
                dao.scheduleSAFEACtivity(day, COMPLETED);
            }
            if(model.isRelaxation()){
                dao.scheduleRelaxationActivity(day, COMPLETED);
            }
            if(model.isStop()){
                dao.scheduleSTOPActivity(day, COMPLETED);
            }
            if(model.isAbmt()){
                dao.scheduleABMTActivity(day, COMPLETED);
            }
            if(model.getStic() > 0){
                dao.scheduleSTICActivity(day, model.getStic());
            }
            return "OK";
        }catch (Exception e){
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateActivityInstance(String requestBody) {
        try {
            //Mock data as of now
            DAO dao = DAOFactory.getTheDAO();
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestBody, ScheduleModel.class);
            boolean COMPLETED = true;

            int day = model.getDay();

            if(model.isSafe()){
                dao.scheduleSAFEACtivity(day, COMPLETED);
            }
            if(model.isRelaxation()){
                dao.scheduleRelaxationActivity(day, COMPLETED);
            }
            if(model.isStop()){
                dao.scheduleSTOPActivity(day, COMPLETED);
            }
            if(model.isAbmt()){
                dao.scheduleABMTActivity(day, COMPLETED);
            }
            if(model.getStic() == 0){
                dao.scheduleSTICActivity(day, 0);
            }
            return "OK";
        }catch (Exception e){
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
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

    @Override
    public String getWorryHeadsInstance() {
        try {
            Random r = new Random();
            String[] o_options = {"The goalie isn't the only player on the team," +
                    " so it couldn't have been only my fault that we lost",
                    "Even though we didn't win, we tied the game, so we still did pretty well",
                    "I've practiced and I feel that I did the best I could and sometimes losing just happens",
                    "I should have practice harder."};

            WorryHeadsModel whm = new WorryHeadsModel(0,
                    "WorryHeads",
                    "You think, \"She thinks it's my fault we didn't win.\"",
                    "You are the goalie for your soccer team and today's game ended in a tie. " +
                            "After the game, you hear a teammate say that your team should have won",
                    "P text",
                    r.nextInt(4),
                    o_options);

            return new ObjectMapper().writeValueAsString(whm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getMakeBelieveInstance() {
        try {
            DAO dao = DAOFactory.getTheDAO();
            MakeBelieveSituation situation = (MakeBelieveSituation) dao.getMakeBelieveActivityInstance();
            return new ObjectMapper().writeValueAsString(situation);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstance in Reach Service");
        }
        return null;
    }

    public String getMakeBelieveInstanceAnswer(int instanceId){
        try{
            DAO dao = DAOFactory.getTheDAO();
            if(!dao.checkSituationExists(instanceId)){
                return "Bad Request";
            }

            MakeBelieveAnswers answers = (MakeBelieveAnswers) dao.getMakeBelieveActivityAnswers(instanceId);
            if(answers != null)
                return new ObjectMapper().writeValueAsString(answers);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstanceAnswer in Reach service");
            return null;
        }
    }

    public int updateMakeBelieveInstance(int instanceId, String responses){
        try{
            MakeBelieveResponse response;
            DAO dao = DAOFactory.getTheDAO();
            if(!dao.checkSituationExists(instanceId)){
                return 400;
            }
            response = new ObjectMapper().readValue(responses, MakeBelieveResponse.class);
            if(instanceId != response.getSituationId()){
                return 400;
            }
            if(dao.updateMakeBelieveActivityInstance(response))
                return 204;
            return 500;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstanceAnswer in Reach service");
            return 500;
        }

    }
}