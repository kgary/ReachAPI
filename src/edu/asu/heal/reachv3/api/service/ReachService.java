package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public String getMakeBelieveInstance() {
        MakeBelieveSituation situation = new MakeBelieveSituation();

        situation.setSituationId(1000);
        situation.setSituationTitle("You see Alex. Alex is talking with friends about movies");

        List<MakeBelieveOption> whenOptions = new ArrayList<>();
        whenOptions.add(new MakeBelieveOption(1, "When Alex talking to a friend "));
        whenOptions.add(new MakeBelieveOption(2, "When Alex starts doing homework "));
        whenOptions.add(new MakeBelieveOption(3, "When Alex is talking to the teacher"));
        whenOptions.add(new MakeBelieveOption(4, "When Alex invites you over"));

        List<MakeBelieveOption> howOptions = new ArrayList<>();
        howOptions.add(new MakeBelieveOption(5, "I don't like movies"));
        howOptions.add(new MakeBelieveOption(6, "What are you guys doing? "));
        howOptions.add(new MakeBelieveOption(7, "Do you want to talk about books? "));
        howOptions.add(new MakeBelieveOption(8, "What movies do you guys like? "));
        
        List<MakeBelieveQuestion> questions = new ArrayList<>();
        questions.add(new MakeBelieveQuestion("when", whenOptions));
        questions.add(new MakeBelieveQuestion("how", howOptions));

        situation.setQuestions(questions);
        try {
            return new ObjectMapper().writeValueAsString(situation);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getMakeBelieveInstanceAnswer(int instanceId){
        if(instanceId != 1000){
            return "Bad Request";
        }
        MakeBelieveAnswers answers = new MakeBelieveAnswers();
        answers.setSituationId(instanceId);
        answers.setHowResponseId(8);
        answers.setWhenResponseId(4);

        try {
            return new ObjectMapper().writeValueAsString(answers);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    public int updateMakeBelieveInstance(int instanceId, String responses){
        MakeBelieveResponse response;
        if(instanceId != 1000){
            return 401;
        }
        try {
            response = new ObjectMapper().readValue(responses, MakeBelieveResponse.class);
        }catch(IOException e){
            e.printStackTrace();
            return 400;
        }
        if(instanceId != response.getSituationId()){
            return 400;
        }
        return 204;
    }
}