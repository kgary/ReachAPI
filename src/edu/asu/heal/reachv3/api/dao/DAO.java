package edu.asu.heal.reachv3.api.dao;

public interface DAO {
    ValueObject getScheduledActivities(int currentDay) throws DAOException;

    boolean scheduleSTOPActivity();

    boolean scheduleSTICActivity();

    boolean scheduleRelaxationActivity();

    boolean scheduleDailyDiaryActivity();

    boolean scheduleABMTActivity();

    boolean scheduleWorryHeadsActivity();

}
