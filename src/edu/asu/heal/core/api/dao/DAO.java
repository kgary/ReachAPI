package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;

import java.util.List;

public interface DAO {


    /****************************************  Domain DAO methods *****************************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain createDomain(Domain instance);

    /****************************************  Activity DAO methods ***************************************************/
    List<Activity> getActivities(String domain) throws DAOException;

    Activity createActivity(Activity activity) throws DAOException;

    Activity getActivity(String activityId);

    Activity updateActivity(Activity activity);

    Activity deleteActivity(String activityId);


    /****************************************  ActivityInstance DAO methods *******************************************/
    List<ActivityInstance> getScheduledActivities(int patientPin, int currentDay) throws DAOException;

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance instance);

    <T> ActivityInstance getActivityInstance(String activityInstanceId, Class<T> type);


    /****************************************  Patient DAO methods ****************************************************/
    List<Patient> getPatients() throws DAOException;

    List<Patient> getPatients(String trialId) throws DAOException;

    Patient getPatient(int patientPin);

    Patient createPatient();

    Patient updatePatient(Patient patient);

    /****************************************  Trial DAO methods ******************************************************/
    List<Trial> getTrials() throws DAOException;

    List<Trial> getTrials(String domain) throws DAOException;

    Trial createTrial(Trial trialInstance) throws DAOException;

    /****************************************  Other DAO methods ******************************************************/

    boolean scheduleSTOPActivity(int day, boolean completed) throws DAOException;

    boolean scheduleSTICActivity(int day, int sticVariable) throws DAOException;

    boolean scheduleRelaxationActivity(int day, boolean completed) throws DAOException;

    boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule);

    boolean scheduleABMTActivity(int day, boolean completed) throws DAOException;

    boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule);

    boolean scheduleSAFEACtivity(int day, boolean completed) throws DAOException;

    Object getMakeBelieveActivityInstance() throws DAOException;

    boolean checkSituationExists(int situationId) throws DAOException;

    Object getMakeBelieveActivityAnswers(int situationId) throws DAOException;

    boolean updateMakeBelieveActivityInstance(Object makeBelieveResponse) throws DAOException;

}
