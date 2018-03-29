package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;

import java.util.List;

public interface DAO {

    // methods pertaining to Domain Model
    Object getDomains();

    Object getDomain(String id);

    String createDomain(Domain instance);

    Object getScheduledActivities(int patientPin, int currentDay) throws DAOException;

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

    boolean createActivity(Activity activity) throws DAOException;

    // methods pertaining trial model
    Object getTrials() throws DAOException;

    Object getTrials(String domain) throws DAOException;

    boolean createTrial(Trial trialInstance) throws DAOException;

    List<Patient> getPatients() throws DAOException;

    List<Patient> getPatients(String trialId) throws DAOException;

    Patient getPatient(int patientPin);

    int createPatient(String patientDetails);
}
