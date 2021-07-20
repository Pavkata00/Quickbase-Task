package com.quickbase;

import com.quickbase.devint.ConcreteStatService;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;
import com.quickbase.devint.model.EntityData;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c ) {
            System.out.println("failed.");
            System.exit(1);
        }

        // Getting the data and printing the first query result:
        List<EntityData> allData = dbm.getAllData();

        System.out.println("Printing the result taken from the DB: ");
        for (EntityData allDatum : allData) {
            System.out.printf("%s - %d%n",allDatum.getName(), allDatum.getPopulation());
        }


        //HashMap used for best performance for final result
        HashMap<String, Integer> finalResult = new HashMap<>();

        //Adding first the whole data from local DB because in case  of duplicate country, we will use the
        //information from it as requested.

        for (EntityData allDatum : allData) {
            finalResult.put(allDatum.getName(),allDatum.getPopulation());
        }

        ConcreteStatService concreteStatService = new ConcreteStatService();

        for (Pair<String, Integer> data : concreteStatService.GetCountryPopulations()) {
            if (!finalResult.containsKey(data.getKey())) {
                finalResult.put(data.getKey(), data.getValue());
            }
        }

        System.out.println("Final result after consuming the API and local DB information:");

        for (Map.Entry<String, Integer> stringIntegerEntry : finalResult.entrySet()) {
            System.out.println(stringIntegerEntry.getKey() + " - " + stringIntegerEntry.getValue());
        }

    }
}