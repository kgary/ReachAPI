package edu.asu.heal.core.api.service;

import edu.asu.heal.core.api.models.*;

import java.util.List;

public interface HealService {

    // methods pertaining to domain resource

    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain addDomain(String title, String description, String state);

    String addTestDomain(String title, String description, String state);

    // methods pertaining to activityInstance resource

    List<ActivityInstance> getActivityInstances(int patientPin, int trialId);

    ActivityInstance getActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance activityInstanceJson);

    String updateActivityInstance(String requestBody);

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    // methods pertaining to patient resource
    List<Patient> getPatients(String trialId);

    Patient getPatient(int patientPin);

    Patient createPatient();

    Patient updatePatient(Patient patient);

    String deletePatient(String patientPin);

    String getMakeBelieveInstance();

    String getMakeBelieveInstanceAnswer(int instanceId);

    int updateMakeBelieveInstance(int instanceId, String responses);

    String getWorryHeadsInstance();

    // methods pertaining to Activity Resource
    List<Activity> getActivities(String domain);

    Activity createActivity(String title, String description);

    // methods pertaining to Trial Resource
    List<Trial> getTrials(String domain);

    Trial addTrial(String domainId, String title, String description, String startDate, String endDate, int targetCount);

}