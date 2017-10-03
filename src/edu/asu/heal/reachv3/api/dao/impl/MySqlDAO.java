package edu.asu.heal.reachv3.api.dao.impl;

import edu.asu.heal.reachv3.api.dao.DAOException;

import java.util.Properties;

public class MySqlDAO extends JDBCDao {
    public MySqlDAO(Properties properties) throws DAOException{
        super(properties);
    }
}
