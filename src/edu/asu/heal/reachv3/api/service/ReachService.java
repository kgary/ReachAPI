package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.reachv3.api.model.*;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReachService implements HealService {

    private static final String DATE_FORMAT = "MM/dd/yyyy";

    /****************************************  Service methods for Activity  ******************************************/
    @Override
    public List<Activity> getActivities(String domain) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Activity> result = dao.getActivities(domain);

            return result;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity createActivity(String title, String description) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Activity newActivity = new Activity();
            newActivity.setTitle(title);
            newActivity.setDescription(description);
            newActivity.setUpdatedAt(new Date());
            newActivity.setCreatedAt(new Date());
            Activity createdActivity = dao.createActivity(newActivity);

            return createdActivity;
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE - CREATEACTIVITY");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity getActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getActivity(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity updateActivity(Activity activity) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Activity activityInDatabase = dao.getActivity(activity.getActivityId());
            if (activityInDatabase == null || activityInDatabase.equals(NullObjects.getNullActivity()))
                return activityInDatabase;

            activityInDatabase.setTitle(
                    activity.getTitle() != null ? activity.getTitle() : activityInDatabase.getTitle());
            activityInDatabase.setDescription(
                    activity.getDescription() != null ? activity.getDescription() : activityInDatabase.getDescription());
            activityInDatabase.setUpdatedAt(new Date());

            return dao.updateActivity(activityInDatabase);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN UPDATE ACTIVITY IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity deleteActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.deleteActivity(activityId);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
            e.printStackTrace();
            return null;
        }
    }

    /****************************************  Service methods for ActivityInstance  **********************************/
    @Override
    public List<ActivityInstance> getActivityInstances(int patientPin, int trialId) {
        List<ActivityInstance> response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<ActivityInstance> instances = dao.getScheduledActivities(patientPin, trialId);

            return instances;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance getActivityInstance(String activityInstanceId, String activityInstanceType) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            if(activityInstanceType == null || activityInstanceType.length() == 0)
                return dao.getActivityInstance(activityInstanceId, ActivityInstance.class);

            if(activityInstanceType.equals("make_believe"))
                return dao.getActivityInstance(activityInstanceId, MakeBelieveActivityInstance.class);

            return NullObjects.getNullActivityInstance();
        } catch (Exception e) {
            System.out.println("SOME ERROR IN HEAL SERVICE getActivityInstance");
            e.printStackTrace();
            return null;
        }
    }

    // First version of createActivityInstance when it was a whole schedule. Revised to the one below this.
    // need to integrate schedule into that

    /*@Override
    public String createActivityInstance(String requestPayload) {
        try {
            //Mock data as of now
            DAO dao = DAOFactory.getTheDAO();
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestPayload, ScheduleModel.class);

            int day = model.getDay();
            boolean COMPLETED = false;

            if (model.isSafe()) {
                dao.scheduleSAFEACtivity(day, COMPLETED);
            }
            if (model.isRelaxation()) {
                dao.scheduleRelaxationActivity(day, COMPLETED);
            }
            if (model.isStop()) {
                dao.scheduleSTOPActivity(day, COMPLETED);
            }
            if (model.isAbmt()) {
                dao.scheduleABMTActivity(day, COMPLETED);
            }
            if (model.getStic() > 0) {
                dao.scheduleSTICActivity(day, model.getStic());
            }
            return "OK";
        } catch (Exception e) {
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public ActivityInstance createActivityInstance(ActivityInstance activityInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
            if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
            if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());
            ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);
            return newActivityInstance;
        } catch (Exception e) {
            System.out.println("SOME ERROR CREATING NE ACTIVITY INSTANCE IN REACH SERVICE - CREATEACTIVITYINSTANCE");
            e.printStackTrace();
            return null;

        }
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

            if (model.isSafe()) {
                dao.scheduleSAFEACtivity(day, COMPLETED);
            }
            if (model.isRelaxation()) {
                dao.scheduleRelaxationActivity(day, COMPLETED);
            }
            if (model.isStop()) {
                dao.scheduleSTOPActivity(day, COMPLETED);
            }
            if (model.isAbmt()) {
                dao.scheduleABMTActivity(day, COMPLETED);
            }
            if (model.getStic() == 0) {
                dao.scheduleSTICActivity(day, 0);
            }
            return "OK";
        } catch (Exception e) {
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ActivityInstance deleteActivityInstance(String activityInstanceId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.deleteActivityInstance(activityInstanceId);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
            e.printStackTrace();
            return null;
        }
    }


    /****************************************  Service methods for Domain  ********************************************/
    @Override
    public List<Domain> getDomains() {
        try {
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomains();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain getDomain(String id) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomain(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain addDomain(String title, String description, String state) {

        try {
            DAO dao = DAOFactory.getTheDAO();
            Domain instance = new Domain(title, description, state);
            instance.setCreatedAt(new Date());
            if (instance.getState() == null) instance.setState(DomainState.CREATED.state());

            return dao.createDomain(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addTestDomain(String title, String description, String state) {
        return null;
    }

    /****************************************  Service methods for Patient  *******************************************/
    @Override
    public List<Patient> getPatients(String trialId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Patient> result;

            if (trialId == null) {
                // return list of all patients present
                result = dao.getPatients();
            } else {
                // return list of patients for given trialId
                result = dao.getPatients(trialId);
            }

            return result;
        } catch (Exception e) {
            System.out.println("SOME PROBLEM WITH REACH SERVICE - GET PATIENTS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient getPatient(int patientPin) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getPatient(patientPin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient createPatient() {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.createPatient();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient updatePatient(Patient patient) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Patient patientInDatabase = dao.getPatient(patient.getPin());
            if (patientInDatabase == null || patientInDatabase.equals(NullObjects.getNullPatient()))
                return patientInDatabase;

            patientInDatabase.setStartDate(
                    patient.getStartDate() != null ? patient.getStartDate() : patientInDatabase.getStartDate());
            patientInDatabase.setEndDate(
                    patient.getEndDate() != null ? patient.getEndDate() : patientInDatabase.getEndDate());
            patientInDatabase.setState(
                    patient.getState() != null ? patient.getState() : patientInDatabase.getState());
            patientInDatabase.setCreatedAt(
                    patient.getCreatedAt() != null ? patient.getCreatedAt() : patientInDatabase.getCreatedAt());
            patientInDatabase.setUpdatedAt(new Date());

            return dao.updatePatient(patientInDatabase);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN UPDATE PATIENT IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deletePatient(String patientPin) {
        return "DELETE PATIENT";
    }

    /****************************************  Service methods for Trial  *********************************************/

    @Override
    public List<Trial> getTrials(String domain) {
        HEALResponse response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Trial> trials = null;

            if (domain == null)
                trials = dao.getTrials();
            else
                trials = dao.getTrials(domain);

            return trials;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETTRIALS() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Trial addTrial(Trial trialInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            // check if the domain exist, if yes get the id of domain
            Domain domain = dao.getDomain(trialInstance.getDomainId());
            if (domain != null) {

                Date startDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getStartDate().toString());
                Date endDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getEndDate().toString());

                trialInstance.setUpdatedAt(new Date());
                trialInstance.setCreatedAt(new Date());
                trialInstance.setStartDate(startDateFormat);
                trialInstance.setEndDate(endDateFormat);
                trialInstance.setDomainId(domain.getDomainId());

                return dao.createTrial(trialInstance);
            } else {
                return NullObjects.getNullTrial();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /****************************************  Other Service methods  *************************************************/

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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMakeBelieveInstance() {
        try {
            DAO dao = DAOFactory.getTheDAO();
            MakeBelieveSituation situation = (MakeBelieveSituation) dao.getMakeBelieveActivityInstance();
            return new ObjectMapper().writeValueAsString(situation);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstance in Reach Service");
        }
        return null;
    }

    public String getMakeBelieveInstanceAnswer(int instanceId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            if (!dao.checkSituationExists(instanceId)) {
                return "Bad Request";
            }

            MakeBelieveAnswers answers = (MakeBelieveAnswers) dao.getMakeBelieveActivityAnswers(instanceId);
            if (answers != null)
                return new ObjectMapper().writeValueAsString(answers);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstanceAnswer in Reach service");
            return null;
        }
    }

    public int updateMakeBelieveInstance(int instanceId, String responses) {
        try {
            MakeBelieveResponse response;
            DAO dao = DAOFactory.getTheDAO();
            if (!dao.checkSituationExists(instanceId)) {
                return Response.Status.BAD_REQUEST.getStatusCode();
            }
            response = new ObjectMapper().readValue(responses, MakeBelieveResponse.class);
            if (instanceId != response.getSituationId()) {
                return Response.Status.BAD_REQUEST.getStatusCode();
            }
            if (dao.updateMakeBelieveActivityInstance(response))
                return Response.Status.NO_CONTENT.getStatusCode();
            return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some problem in getMakeBelieveInstanceAnswer in Reach service");
            return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }

    }
}
