package edu.asu.heal.core.api.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOException;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.dao.ValueObject;
import edu.asu.heal.reachv3.api.model.*;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public abstract class JDBCDao implements DAO {
    private String __jdbcDriver;
    protected String _jdbcUser;
    protected String _jdbcPasswd;
    protected String _jdbcUrl;
    private Map<String, List<String>> emotionsMap = new HashMap<>();


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

        try {
            Properties properties1 = new Properties();
            properties1.load(JDBCDao.class.getResourceAsStream("emotions.properties"));

            emotionsMap.put(Emotions.happy.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.happy").split(","))));
            emotionsMap.put(Emotions.sad.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.sad").split(","))));
            emotionsMap.put(Emotions.sick.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.sick").split(","))));
            emotionsMap.put(Emotions.angry.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.angry").split(","))));
            emotionsMap.put(Emotions.scared.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.scared").split(","))));
            emotionsMap.put(Emotions.worried.toString(),
                    new ArrayList<>(Arrays.asList(properties1.getProperty("emotions.worried").split(","))));
        }catch (IOException e){
            e.printStackTrace();
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
    public Object getScheduledActivities(int currentDay) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ValueObject vo = null;
        ScheduleModel sm = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduledActivities");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentDay);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int STIC = resultSet.getInt("STIC");
                boolean STOP = resultSet.getInt("STOP") == 1;
                boolean Relaxation = resultSet.getInt("RELAXATION") == 1;
                boolean DailyDiary = resultSet.getInt("DIARY_EVENT1") == 1;
                boolean SAFE = resultSet.getInt("SAFE") == 1;
                boolean WorryHeads = resultSet.getInt("STOP_WORRYHEADS") == 1;
                boolean ABMT = resultSet.getInt("ABMT") == 1;

                /*vo = new ValueObject();
                vo.putAttribute("STIC", STIC);
                vo.putAttribute("STOP", STOP);
                vo.putAttribute("RELAXATION", Relaxation);
                vo.putAttribute("DAILY_DIARY", DailyDiary);
                vo.putAttribute("SAFE", SAFE);
                vo.putAttribute("WORRY_HEADS", WorryHeads);
                vo.putAttribute("ABMT", true);*/

                sm = new ScheduleModel();
                sm.setStic(STIC);
                sm.setRelaxation(Relaxation);
                sm.setSafe(SAFE);
                sm.setDiaryEvent1(DailyDiary);
                sm.setStopWorryheads(WorryHeads);
                sm.setStop(STOP);
                sm.setAbmt(ABMT);

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
        return sm;
    }

//    @Override
//    public boolean scheduleSTOPActivity(String STOPWeeklySchedule) throws DAOException {
//
//        return false;
//    }
//
//    @Override
//    public boolean scheduleSTICActivity(int STICWeeklySchedule) {
//        return false;
//    }
//
//    @Override
//    public boolean scheduleRelaxationActivity(String relaxationWeeklySchedule) {
//        return false;
//    }
//
//    @Override
//    public boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule) {
//        return false;
//    }
//
//    @Override
//    public boolean scheduleABMTActivity(String ABMTWeeklySchedule) {
//        return false;
//    }
//
//    @Override
//    public boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule) {
//        return false;
//    }
//
//    @Override
//    public boolean scheduleSAFEACtivity(String SAFEWeeklySchedule) {
//        return false;
//    }


    @Override
    public boolean scheduleSTOPActivity(int day, boolean completed) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleSTOPActivity");
            preparedStatement = connection.prepareStatement(query);
            if(completed)
                preparedStatement.setInt(1, 0);
            else
                preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, day);
            boolean result = preparedStatement.execute();

            if(result)
                return true;
            else
                return false;

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
    }

    @Override
    public boolean scheduleSTICActivity(int day, int sticVariable) throws DAOException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleSTICActivity");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sticVariable);
            preparedStatement.setInt(2, day);
            boolean result = preparedStatement.execute();

            if(result)
                return true;
            else
                return false;

        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduleSTICActivity");
        }finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public boolean scheduleRelaxationActivity(int day, boolean completed) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleRelaxationActivity");
            preparedStatement = connection.prepareStatement(query);
            if(completed)
                preparedStatement.setInt(1, 0);
            else
                preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, day);
            boolean result = preparedStatement.execute();

            if(result)
                return true;
            else
                return false;

        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduleRelaxationActivity");
        }finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleABMTActivity(int day, boolean completed) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleABMTActivity");
            preparedStatement = connection.prepareStatement(query);
            if(completed)
                preparedStatement.setInt(1, 0);
            else
                preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, day);
            boolean result = preparedStatement.execute();

            if(result)
                return true;
            else
                return false;

        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduleABMTActivity");
        }finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleSAFEACtivity(int day, boolean completed) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query = DAOFactory.getDAOProperties().getProperty("sql.scheduleSAFEActivity");
            preparedStatement = connection.prepareStatement(query);
            if(completed)
                preparedStatement.setInt(1, 0);
            else
                preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, day);
            boolean result = preparedStatement.execute();

            if(result)
                return true;
            else
                return false;

        }catch (Throwable t){
            t.printStackTrace();
            throw new DAOException("Unable to process results from query sql.scheduleSAFEActivity");
        }finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public Object getMakeBelieveActivityInstance() throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        ResultSet resultSetNames = null;
        MakeBelieveSituation situation = null;
        Random random = new Random();
        int situationId = -1;
        String situationTitle = "";
        try {
            String query = DAOFactory.getDAOProperties().getProperty("sql.makeBelieveInstance");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, random.nextInt(40));
            resultSet = preparedStatement.executeQuery();

            String query1 = DAOFactory.getDAOProperties().getProperty("sql.makeBelieveNames");
            preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setInt(1, random.nextInt(11));
            resultSetNames = preparedStatement1.executeQuery();
            String name = "";
            if(resultSetNames.next()){
                name = resultSetNames.getString("NAME");
            }

            if (resultSet.next()) {
                situationId = resultSet.getInt("ID");
                situationTitle = resultSet.getString("TITLE");
            }
            resultSet.close();
            resultSetNames.close();
            situation = new MakeBelieveSituation();
            situation.setName(name);
            situation.setSituationId(situationId);
            situation.setSituationTitle(situationTitle);
            situation.setQuestions(getSituationQuestions(situationId));

            return situation;
        }catch (Throwable t){
            t.printStackTrace();
            throw  new DAOException("Unable to process results from qyert makeBelieveInstance");
        }finally {
            try{
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public List<MakeBelieveQuestion> getSituationQuestions(int situationId) throws DAOException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSetWhenOptions = null;
        ResultSet resultSetHowOptions = null;
        List<MakeBelieveQuestion> makeBelieveQuestions = new ArrayList<>();

        try {
            String query = DAOFactory.getDAOProperties().getProperty("sql.makeBelieveOptions");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "when");
            preparedStatement.setInt(2, situationId);
            resultSetWhenOptions = preparedStatement.executeQuery();

            MakeBelieveQuestion whenQuestion = new MakeBelieveQuestion();
            whenQuestion.setType("when");
            List<MakeBelieveOption> whenOptions = new ArrayList<>();
            while (resultSetWhenOptions.next()) {
                whenOptions.add(new MakeBelieveOption(resultSetWhenOptions.getInt("ID"),
                        resultSetWhenOptions.getString("TITLE")));
            }
            whenQuestion.setOptions(whenOptions);
            resultSetWhenOptions.close();

            preparedStatement.setString(1, "how");
            preparedStatement.setInt(2, situationId);
            resultSetHowOptions = preparedStatement.executeQuery();

            MakeBelieveQuestion howQuestion = new MakeBelieveQuestion();
            howQuestion.setType("how");
            List<MakeBelieveOption> howOptions = new ArrayList<>();
            while (resultSetHowOptions.next()) {
                howOptions.add(new MakeBelieveOption(resultSetHowOptions.getInt("ID"),
                        resultSetHowOptions.getString("TITLE")));
            }
            howQuestion.setOptions(howOptions);
            resultSetHowOptions.close();

            makeBelieveQuestions.add(whenQuestion);
            makeBelieveQuestions.add(howQuestion);

            connection.close();
            whenOptions = null;
            howOptions = null;

            return makeBelieveQuestions;
        }catch (SQLException se){
            System.out.println("Some error in getSituationQuestions");
            se.printStackTrace();
            return null;
        }finally {
            makeBelieveQuestions = null;
        }
    }

    @Override
    public boolean checkSituationExists(int situationId) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean EXISTS = false;
        try {
            String query = DAOFactory.getDAOProperties().getProperty("sql.checkMakeBelieveSituation");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, situationId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                EXISTS = true;
            }
            resultSet.close();

        }catch (Throwable t){
            t.printStackTrace();
        }
        return EXISTS;
    }

    @Override
    public Object getMakeBelieveActivityAnswers(int situationId) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MakeBelieveAnswers answers = null;
        try {
            String query = DAOFactory.getDAOProperties().getProperty("sql.makeBelieveAnswers");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, situationId);
            resultSet = preparedStatement.executeQuery();

            answers = new MakeBelieveAnswers();
            answers.setSituationId(situationId);
            while(resultSet.next()){
                if(resultSet.getString("TITLE").equals("when")){
                    answers.setWhenResponseId(resultSet.getInt("OPTION_ID"));
                }
                if(resultSet.getString("TITLE").equals("how")){
                    answers.setHowResponseId(resultSet.getInt("OPTION_ID"));
                }
            }
            resultSet.close();
            connection.close();
            return answers;
        }catch (Throwable t){
            t.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateMakeBelieveActivityInstance(Object makeBelieveResponse) throws DAOException {
        MakeBelieveResponse response = (MakeBelieveResponse) makeBelieveResponse;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String query = DAOFactory.getDAOProperties().getProperty("sql.insertMakeBelieveInstanceResponse");
            preparedStatement = connection.prepareStatement(query);
            HashMap<Long, Integer> responseMap = response.getUserAnswers();
            Set<Long> timestamps = responseMap.keySet();
            for (Long timestamp : timestamps) {
                preparedStatement.setInt(1, response.getSituationId());
                preparedStatement.setDate(2, new Date(timestamp));
                preparedStatement.setInt(3, responseMap.get(timestamp));
                preparedStatement.execute();
            }
            connection.close();
            return true;
        }catch (Throwable t){
            t.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getEmotionsActivityInstance(String emotion, int intensity) throws DAOException {
        try {
            if (emotion.equals(Emotions.worried.toString())) {
                if (intensity >= 6) {
                    List<String> temp = emotionsMap.get(emotion);
                    temp.remove("faceIt");
                    return temp;
                }
            }

            return emotionsMap.get(emotion);
        }catch (NullPointerException npe){
            npe.printStackTrace();
            return null;
        }

    }
}

enum Emotions{
    happy, sad, sick, scared, worried, angry
}