package edu.asu.heal.reachv3.api.dao;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class DAOFactory {
    private static Properties _properties;

    private static DAO _theDAO;

    private DAOFactory() {}

    public static DAO getTheDAO() throws DAOException {
        if (_theDAO == null) {
            throw new DAOException("DAO not properly initialized");
        }
        return _theDAO;
    }

    public static Properties getDAOProperties() {
        return _properties;
    }

    static {
        _properties = new Properties();
        try {
            InputStream propFile = DAOFactory.class.getResourceAsStream("dao.properties");
            _properties.load(propFile);
            propFile.close();

            String daoClass = _properties.getProperty("dao.class");

            if (daoClass != null) {
                Class<?> daoClazz = Class.forName(daoClass);
                //get constructor
                Constructor<?> constructor = daoClazz.getConstructor(Properties.class);
                _theDAO = (DAO) constructor.newInstance(_properties);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            try {
                throw new DAOException(t);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}