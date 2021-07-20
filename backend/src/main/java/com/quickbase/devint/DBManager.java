package com.quickbase.devint;

import com.quickbase.devint.model.EntityData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {
    public Connection getConnection();

    public List<EntityData> getAllData() throws SQLException;
}
