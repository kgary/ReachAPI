package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAOException;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReachService implements HealService {

    private static final String DATE_FORMAT = "MM/dd/yyyy";

    @Override
    public HEALResponse getDomains(){
        try {
            DAO dao = DAOFactory.getTheDAO();

            List<Domain> domains = (List<Domain>) dao.getDomains();
            if (domains != null){

                return HEALResponse.getSuccessMessage(Response.Status.OK.getStatusCode(),
                        "Domain List", domains);
            }

            return HEALResponse.getErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
                    "Domains Not Found", null);
        } catch(Exception e){
            e.printStackTrace();

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unhandled Exception: " + e.getMessage(), null);
        }
    }

    @Override
    public HEALResponse getDomain(String id){
        try {
            DAO dao = DAOFactory.getTheDAO();
            Document domain = (Document) dao.getDomain(id);

            if (domain != null) {
                List<String> data = new ArrayList<String>();
                data.add(domain.toJson());

                return HEALResponse.getSuccessMessage(Response.Status.OK.getStatusCode(),
                        "Domain Description", data);
            }

            return HEALResponse.getErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
                    "Domain Not Found", null);
        } catch (Exception e ){
            e.printStackTrace();

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "UnHandled Exception: " + e.getMessage(), null);
        }
    }

    @Override
    public String addDomain(String title, String description, String state) {

        try {
            DAO dao = DAOFactory.getTheDAO();
            Domain instance = new Domain(title, description, state);
            instance.setId(new ObjectId(new Date()));

            return dao.createDomain(instance);
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String addTestDomain(String title, String description, String state) {
        return null;
    }

    @Override
    public HEALResponse getActivityInstances(int patientPin, int trialId) {
        HEALResponse response = null;
        try {

            // TODO -- scope in the possibility that when queryParams(patientPin, trialId) are not present, then
            DAO dao = DAOFactory.getTheDAO();
            Object instances = dao.getScheduledActivities(patientPin, trialId);
            if(instances == null){
                response = new HEALResponse(500,
                        "Some error connecting to the database", HEALResponse.ERROR_MESSAGE_TYPE ,null);
                return response;
            }

            if(instances instanceof NullPointerException){
                response = new HEALResponse(400,
                        "Patient pin invalid or not does not exist",
                        HEALResponse.ERROR_MESSAGE_TYPE, null);
                return response;
            }

            if(instances instanceof List){
                response = new HEALResponse(200,
                        "Success",
                        HEALResponse.SUCCESS_MESSAGE_TYPE,
                        (List) instances);
            }

            return response;
        } catch(Exception e){
            System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
            e.printStackTrace();
            response = new HEALResponse(500,
                    "Some error connecting to the database", HEALResponse.ERROR_MESSAGE_TYPE ,null);
            return response;
        }
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

    // patient resource method
    @Override
    public HEALResponse getPatients(String trialId){

        System.out.println("trialId: " + trialId);
        try{
            DAO dao = DAOFactory.getTheDAO();
            List<Patient> result;

            if (trialId == null){
                // return list of all patients present
                result = dao.getPatients();
            } else {
                // return list of patients for given trialId
                result = dao.getPatients(trialId);
            }

            if (result.isEmpty()) {
                return HEALResponse.getErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
                        "No Patients Found!!!", null);
            }

            return HEALResponse.getSuccessMessage(Response.Status.OK.getStatusCode(), "Success", result);
        } catch (DAOException e){

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    e.getMessage(), null);
        } catch (Exception e) {

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "UnHandled Exception: " + e.getMessage(), null);
        }
    }

    @Override
    public Patient getPatient(int patientPin) {
        try{
            DAO dao = DAOFactory.getTheDAO();
            return dao.getPatient(patientPin);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int createPatient(String requestBody) {
        try{
            DAO dao = DAOFactory.getTheDAO();
            return dao.createPatient(requestBody);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
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

    // methods pertaining to Activity Model
    @Override
    public HEALResponse getActivities(String domain) {
        try{
            DAO dao = DAOFactory.getTheDAO();
            List<Activity> result = dao.getActivities(domain);

            if (result.isEmpty()) {
                return HEALResponse.getErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
                        "No Activities for domain were found!", null);
            }

            return HEALResponse.getSuccessMessage(Response.Status.OK.getStatusCode(), "Found Activities!",
                    result);
        }catch (Exception e){
            System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
            e.printStackTrace();

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "UnHandled Exception: " + e.getMessage(), null);
        }
    }

    @Override
    public HEALResponse createActivity(String title, String description){
        try {
            DAO dao = DAOFactory.getTheDAO();
            boolean createStatus = dao.createActivity(new Activity(title, description));

            if (createStatus){
                return HEALResponse.getSuccessMessage(Response.Status.CREATED.getStatusCode(), "Created", null);
            }

            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Could not create activity", null);
        } catch (Exception e){
            e.printStackTrace();
            return HEALResponse.getErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unhandled Exception: " + e.getMessage(), null);
        }
    }

    @Override
    public List<Trial> getTrials(String domain) {
        try{
            DAO dao = DAOFactory.getTheDAO();
            return dao.getTrials(domain);
        }catch (Exception e){
            System.out.println("SOME ERROR IN GETTRIALS() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addTrial(String domainId, String title, String description, String startDate, String endDate,
                           int targetCount) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            // check if the domain exist, if yes get the id of domain
            Document domain = (Document) dao.getDomain(domainId);
            if (domain != null) {

                Date startDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(startDate);
                Date endDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(endDate);

                ObjectId newId = ObjectId.get();
                String trialId = newId.toHexString();
                Trial trialInstance = new Trial(newId, (ObjectId) domain.get("_id"), trialId, title, description,
                        startDateFormat, endDateFormat, targetCount);

                dao.createTrial(trialInstance);

                return "OK";
            }

            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
