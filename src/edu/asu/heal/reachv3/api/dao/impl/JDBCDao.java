package edu.asu.heal.reachv3.api.dao.impl;

import edu.asu.heal.reachv3.api.dao.DAO;
import edu.asu.heal.reachv3.api.dao.DAOException;
import edu.asu.heal.reachv3.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.dao.ValueObject;

import javax.management.ValueExp;
import java.sql.*;
import java.util.Properties;

public abstract class JDBCDao implements DAO {
    private String __jdbcDriver;
    protected String _jdbcUser;
    protected String _jdbcPasswd;
    protected String _jdbcUrl;

    public JDBCDao(Properties properties) throws DAOException{
        _jdbcUrl    = properties.getProperty("jdbc.url");
        _jdbcUser   = properties.getProperty("jdbc.user");
        _jdbcPasswd = properties.getProperty("jdbc.passwd");
        __jdbcDriver = properties.getProperty("jdbc.driver");

        try{
            Class.forName(__jdbcDriver);
        }catch (ClassNotFoundException ce){
            throw new DAOException("*** Cannot find the JDBC driver " + __jdbcDriver, ce);
        }catch (Throwable t){
            throw new DAOException(t);
        }
    }

    protected Connection getConnection() throws DAOException {
        try {
            return DriverManager.getConnection(_jdbcUrl, _jdbcUser, _jdbcPasswd);
        }catch (SQLException se){
            se.printStackTrace();
            throw new DAOException("Unable to get connection to database", se);
        }
    }

    @Override
    public ValueObject getScheduledActivities(int currentDay) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ValueObject vo = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduledActivities");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentDay);
            resultSet = preparedStatement.executeQuery();

            int STIC = resultSet.getInt("STIC");
            boolean STOP = resultSet.getInt("STOP") == 1;
            boolean Relaxation = resultSet.getInt("RELAXATION") == 1;
            boolean DailyDiary = resultSet.getInt("DIARY_EVENT1") == 1;
            boolean SAFE = resultSet.getInt("SAFE") == 1;
            boolean WorryHeads = resultSet.getInt("STOP_WORRYHEADS") == 1;

            if(resultSet.next()){
                vo = new ValueObject();
                vo.putAttribute("STIC", STIC);
                vo.putAttribute("STOP", STOP);
                vo.putAttribute("RELAXATION", Relaxation);
                vo.putAttribute("DAILY_DIARY", DailyDiary);
                vo.putAttribute("SAFE", SAFE);
                vo.putAttribute("WORRY_HEADS", WorryHeads);
                vo.putAttribute("ABMT", true);
            }
        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduledActivities");
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return vo;
    }

    @Override
    public boolean scheduleSTOPActivity(String STOPWeeklySchedule) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        STOPWeeklySchedule = STOPWeeklySchedule.replaceAll("\\[", "\\(").replaceAll("]", "\\)");
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleSTOPActivity");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, STOPWeeklySchedule);
            if(preparedStatement.executeUpdate(query) > 0)
                return true;

        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduleSTOPActivity");
        }finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean scheduleSTICActivity(int STICWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleRelaxationActivity(String relaxationWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleABMTActivity(String ABMTWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleSAFEACtivity(String SAFEWeeklySchedule) {
        return false;
    }
}
