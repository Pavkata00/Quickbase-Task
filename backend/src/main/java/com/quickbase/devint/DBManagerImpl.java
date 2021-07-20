package com.quickbase.devint;

import com.quickbase.devint.model.EntityData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {

    public Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException cnf) {
            System.out.println("could not load driver");
        } catch (SQLException sqle) {
            System.out.println("sql exception:" + sqle.getStackTrace());
        }
        return c;
    }
    //TODO: Add a method (signature of your choosing) to query the db for population data by country

    public List<EntityData> getAllData() throws SQLException {

        //Writing my SQL raw code for usage later.
        //(Descending order for user friendly usage.

        String sql = "SELECT CountryName, SUM(c.population) as Population FROM Country " +
                "JOIN `State` s ON s.CountryId = Country.CountryId " +
                "JOIN City c ON c.StateId = s.StateId " +
                "GROUP  BY CountryName " +
                "ORDER BY Population DESC ";

        //Using the above given method for connection and thus creating a statement for my query.
        Connection connection = this.getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        List<EntityData> entities = new ArrayList<>();

        //Result set is returning the desired result from the query, thus using it to create entities.
        while (resultSet.next()) {

            EntityData entity = new EntityData(resultSet.getString("CountryName"), resultSet.getInt("Population"));
            entities.add(entity);

        }

        return entities;
    }

}
