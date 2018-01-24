package edu.asu.heal.core.api.dao.impl;

import edu.asu.heal.core.api.dao.DAOException;

import java.util.Properties;

public class MySqlDAO extends JDBCDao {
    public MySqlDAO(Properties properties) throws DAOException{
        super(properties);
    }
}
