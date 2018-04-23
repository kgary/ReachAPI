package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOException;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.dao.ValueObject;

public final class ModelFactory {
    private DAO _theDAO = null;

    public ModelFactory() throws ModelException {
        try{
            _theDAO = DAOFactory.getTheDAO();
        }catch (DAOException de){
            System.out.println(de); //TODO IMPLEMENT A LOGGER
            throw new ModelException("Unable to initialize the DAO", de);
        }
    }

    public ValueObject getScheduledActivities(int currentDay) throws ModelException{
//        try{
//            ValueObject vo; = (ValueObject)_theDAO.getScheduledActivities(0, currentDay);
//            if(vo == null){
//                return null;
//            }
//            return vo;
//            //TODO @kgary DO WE NEED A MODEL HERE ??
//        }catch (DAOException de){
//            de.printStackTrace();
//            //TODO NEED TO IMPLEMENT A LOGGER log.error(de);
//            throw new ModelException("Unable to create Model Object", de);
//        }
        return null;
    }

//    public boolean scheduleActivities(String STOP, String SAFE, String WorryHeads, String DailyDiary, String ABMT, String Relaxation, int STIC){
//        try {
//            if(STOP != null){
//                if(!_theDAO.scheduleSTOPActivity(STOP))
//                    return false;
//            }
//            if(SAFE != null){
//                _theDAO.scheduleSAFEACtivity(SAFE);
//            }
//            if(WorryHeads != null){
//                _theDAO.scheduleWorryHeadsActivity(WorryHeads);
//            }
//            if(DailyDiary != null){
//                _theDAO.scheduleDailyDiaryActivity(DailyDiary);
//            }
//            if(ABMT != null){
//                _theDAO.scheduleABMTActivity(ABMT);
//            }
//            if(Relaxation != null){
//                _theDAO.scheduleRelaxationActivity(Relaxation);
//            }
//            if(STIC > -1){
//                _theDAO.scheduleSTICActivity(STIC);
//            }
//        }catch (Exception e){
//            System.out.println("EXCEPTION THROWN IN MODELFACTORY.SCHEDULEACTIVITIES");
//            return false;
//        }
//        return true;
//    }


}
