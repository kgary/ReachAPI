package edu.asu.heal.core.api.hal;

import edu.asu.heal.core.api.models.*;

import java.util.List;

public interface HALHelper {
    String getActivityInstancesJSON(ActivityInstance a, String activityInstanceResourcePath,
                                    String patientResourcePath, String activityResourcePath);

    String getActivityInstancesJSON(List<ActivityInstance> aList, String activityInstanceResourcePath,
                                    String patientResourcePath, String activityResourcePath);

    String getActivitiesJSON(Activity activity, String activityResourcePath);
    String getActivitiesJSON(List<Activity> activities, String activityResourcePath);

    String getDomainsJSON(Domain domain, String domainResourcePath, String activityResourcePath, String trialResourcePath);
    String getDomainsJSON(List<Domain> domain, String domainResourcePath, String activityResourcePath, String trialResourcePath);

    String getPatientsJSON(Patient patient, String patientResourcePath, String activityInstanceResourcePath);
    String getPatientsJSON(List<Patient> patient, String patientResourcePath, String activityInstanceResourcePath);

    String getTrialsJSON(Trial trial, String trialResourcePath, String domainResourcePath, String patientResourcePath);
    String getTrialsJSON(List<Trial> trial, String trialResourcePath, String domainResourcePath, String patientResourcePath);




}
