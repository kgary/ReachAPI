package edu.asu.heal.core.api.service;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.schedule.ActivityScheduleJSON;

import java.util.HashMap;
import java.util.List;

public interface HealService {


    /****************************************  Service methods for Activity  ******************************************/
    List<Activity> getActivities(String domain);

    Activity createActivity(String title, String description);

    Activity getActivity(String activityId);

    Activity updateActivity(Activity activity);

    Activity deleteActivity(String activityId);

    /****************************************  Service methods for ActivityInstance  **********************************/
    List<ActivityInstance> getActivityInstances(int patientPin);

    ActivityInstance getActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance activityInstanceJson);

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance updateActivityInstance(String requestBody);

    /****************************************  Service methods for Domain  ********************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain addDomain(String title, String description, String state);

    String addTestDomain(String title, String description, String state);

    /****************************************  Service methods for Patient  *******************************************/
    List<Patient> getPatients(String trialId);

    Patient getPatient(int patientPin);

    Patient createPatient(String trialId);

    Patient updatePatient(Patient patient);

    String deletePatient(String patientPin);

    /****************************************  Service methods for Trial  *********************************************/
    List<Trial> getTrials(String domain);

    Trial addTrial(Trial trialInstance);
    
    String getTrialIdByTitle(String trialName);

    /****************************************  Service methods for Logger  ********************************************/
    Logger[] logMessage (Logger[] loggerInstance);
    
    Logger[] logPersonalizationMessage (Logger[] loggerInstance);

    /****************************************  Notification methods  *************************************************/
    boolean sendNotification(int patientPin,int module, int moduleLen, int dayOfModule, 
			int indexOfActivity, int days, ActivityScheduleJSON activity);

    /****************************************  Other Service methods  *************************************************/
    void personalizeUserExperience(int patientpin);
    
    HashMap<String, Boolean> getActivitySchedule(int patientPin);

}