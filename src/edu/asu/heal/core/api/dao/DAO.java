package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;

import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.StandUpActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpSituation;
import edu.asu.heal.reachv3.api.models.FaceitActivityInstance;
import edu.asu.heal.reachv3.api.models.FaceItModel;
import edu.asu.heal.reachv3.api.models.WorryHeadsActivityInstance;
import edu.asu.heal.reachv3.api.models.WorryHeadsSituation;

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

    List<String> getEmotionsActivityInstance(String emotion, Object intensity);

	MakeBelieveActivityInstance getActivityMakeBelieveInstanceDAO(String activityInstanceId);
	
	StandUpActivityInstance getActivityStandUpInstanceDAO(String activityInstanceId);
	
	List<StandUpSituation> getStandUpSituations();

	List<FaceItModel> getFaceItChallenges();

	FaceitActivityInstance getActivityFaceItInstanceDAO (String activityInstanceId);

	boolean updateFaceitActivityInstance(ActivityInstance instance);
	

    List<WorryHeadsSituation> getAllWorryHeadsSituations();

	WorryHeadsActivityInstance getActivityWorryHeadsInstanceDAO(String activityInstanceId);
	
	int getReleasedBlobTricksDAO(int patientPin);
	
	void updateBlobTrickCountDAO(int patientPin, int count);

}
