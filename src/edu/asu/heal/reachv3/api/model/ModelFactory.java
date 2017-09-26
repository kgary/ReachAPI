package edu.asu.heal.reachv3.api.model;

import edu.asu.heal.reachv3.api.dao.DAO;
import edu.asu.heal.reachv3.api.dao.DAOException;
import edu.asu.heal.reachv3.api.dao.DAOFactory;
import edu.asu.heal.reachv3.api.dao.ValueObject;

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
        try{
            ValueObject vo = _theDAO.getScheduledActivities(currentDay);
            if(vo == null){
                return null;
            }
            return vo;
            //TODO @kgary DO WE NEED A MODEL HERE ??
        }catch (DAOException de){
            de.printStackTrace();
            //TODO NEED TO IMPLEMENT A LOGGER log.error(de);
            throw new ModelException("Unable to create Model Object", de);
        }
    }


}
