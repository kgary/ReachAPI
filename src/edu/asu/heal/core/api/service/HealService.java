package edu.asu.heal.core.api.service;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.models.Trial;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public interface HealService {

    // methods pertaining to domain resource

    String getDomains();

    String getDomain(String id);

    String addDomain(String title, String description, String state);

    String addTestDomain(String title, String description, String state);

    // methods pertaining to activityInstance resource

    String getActivityInstances(int patientPin, int trialId);

    String getActivityInstance(String activityInstanceId);

    String createActivityInstance(String requestPayload);

    String updateActivityInstance(String requestBody);

    String deleteActivityInstance(String activityInstanceId);

    // methods pertaining to patient resource
    String getPatients(String trialId);

    String getPatient(String patientPin);

    String createPatient(String requestBody);

    String updatePatient(String requestBody);

    String deletePatient(String patientPin);

    String getMakeBelieveInstance();

    String getMakeBelieveInstanceAnswer(int instanceId);

    int updateMakeBelieveInstance(int instanceId, String responses);

    String getWorryHeadsInstance();

    // methods pertaining to Activity Resource
    List<Activity> getActivities(String domain);

    String createActivity(String requestBody);


    String addActivity(String title, String description);

    // methods pertaining to Trial Resource
    List<Trial> getTrials(String domain);

    String addTrial(String domainId, String title, String description, String startDate, String endDate, int targetCount);

}