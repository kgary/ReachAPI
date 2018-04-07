package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;

import java.util.List;

public interface DAO {

    // methods pertaining to Domain Model
    Object getDomains();

    Domain getDomain(String id);

    boolean createDomain(Domain instance);

    List<ActivityInstance> getScheduledActivities(int patientPin, int currentDay) throws DAOException;

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

    // methods pertaining to Activity (activities) collection
    List<Activity> getActivities(String domain) throws DAOException;

    Activity createActivity(Activity activity) throws DAOException;

    // methods pertaining trial model
    List<Trial> getTrials() throws DAOException;

    List<Trial> getTrials(String domain) throws DAOException;

    boolean createTrial(Trial trialInstance) throws DAOException;

    List<Patient> getPatients() throws DAOException;

    List<Patient> getPatients(String trialId) throws DAOException;

    Patient getPatient(int patientPin);

    Patient createPatient();

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance instance);

    ActivityInstance getActivityInstance(String activityInstanceId);
}
