package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;

import edu.asu.heal.reachv3.api.models.*;
import edu.asu.heal.reachv3.api.models.schedule.PatientScheduleJSON;

import java.util.List;

public interface DAO {


    /****************************************  Domain DAO methods *****************************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain createDomain(Domain instance);

    /****************************************  Activity DAO methods ***************************************************/
    List<Activity> getActivities(String domain);

    Activity createActivity(Activity activity);

    Activity getActivity(String activityId);

    Activity updateActivity(Activity activity);

    Activity deleteActivity(String activityId);


    /****************************************  ActivityInstance DAO methods *******************************************/
    List<ActivityInstance> getScheduledActivities(int patientPin);

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance instance);

    ActivityInstance getActivityInstance(String activityInstanceId);

    boolean updateActivityInstance(ActivityInstance instance);


    /****************************************  Patient DAO methods ****************************************************/
    List<Patient> getPatients();

    List<Patient> getPatients(String trialId);

    Patient getPatient(int patientPin);

    Patient createPatient(String trialId);

    Patient updatePatient(Patient patient);

    /****************************************  Trial DAO methods ******************************************************/
    List<Trial> getTrials();

    List<Trial> getTrials(String domain);

    Trial createTrial(Trial trialInstance);
    
    String getTrialIdByTitle(String title);

    /****************************************  Logger DAO methods *****************************************************/
    Logger[] logMessage (Logger[] loggerInstance);

    /****************************************  Other DAO methods ******************************************************/

    MakeBelieveSituation getMakeBelieveSituation();

    List<String> getEmotionsActivityInstance(String emotion, Object intensity, String sessionId);

	MakeBelieveActivityInstance getActivityMakeBelieveInstanceDAO(String activityInstanceId);
	
	StandUpActivityInstance getActivityStandUpInstanceDAO(String activityInstanceId);
	
	List<StandUpSituation> getStandUpSituations();

	List<FaceItModel> getFaceItChallenges();

	FaceitActivityInstance getActivityFaceItInstanceDAO (String activityInstanceId);

	boolean updateFaceitActivityInstance(ActivityInstance instance);
	

    List<WorryHeadsSituation> getAllWorryHeadsSituations();

	WorryHeadsActivityInstance getActivityWorryHeadsInstanceDAO(String activityInstanceId);
	
	BlobTricks getReleasedBlobTricksDAO(int patientPin);
	
	void updateBlobTrickCountDAO(BlobTricks blobTricks);

    PatientScheduleJSON getSchedule(int patientPin);
    
    boolean updateUIPersonalization(int patientPin, int module, 
			int dayOfModule, int indexOfActivity, int levelOfPersonalization);
    
    boolean updatePatientScoreActualCount(int patientPin, int module, int day, 
    		int indexOfActivity,int score, int actualCount);

    boolean updateLevelOfSkillPersonalization(int patientPin, int module, int day,
    		int indexOfActivity,Integer levelOfSkillPersonalization);

    boolean updateActivityInstanceInPatientSchedule(int patientPin, int module, int day,
    		int indexOfActivity,String activityInstanceId);

}
