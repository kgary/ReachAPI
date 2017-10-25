package edu.asu.heal.core.api.dao;

public interface DAO {
    Object getScheduledActivities(int currentDay) throws DAOException;

    boolean scheduleSTOPActivity(String STOPWeeklySchedule) throws DAOException;

    boolean scheduleSTICActivity(int STICWeeklySchedule);

    boolean scheduleRelaxationActivity(String relaxationWeeklySchedule);

    boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule);

    boolean scheduleABMTActivity(String ABMTWeeklySchedule);

    boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule);

    boolean scheduleSAFEACtivity(String SAFEWeeklySchedule);

}
