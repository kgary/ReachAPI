package edu.asu.heal.core.api.dao;

import java.util.List;

public interface DAO {
    Object getScheduledActivities(int currentDay) throws DAOException;

//    boolean scheduleSTOPActivity(String STOPWeeklySchedule) throws DAOException;
//
//    boolean scheduleSTICActivity(int STICWeeklySchedule);
//
//    boolean scheduleRelaxationActivity(String relaxationWeeklySchedule);
//
//    boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule);
//
//    boolean scheduleABMTActivity(String ABMTWeeklySchedule);
//
//    boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule);
//
//    boolean scheduleSAFEACtivity(String SAFEWeeklySchedule);

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

    List<String> getEmotionsActivityInstance(String emotion, int intensity) throws DAOException;

}
