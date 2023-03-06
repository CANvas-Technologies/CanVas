package org.canvas.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ArrayList;

public class JDBCExecutor {

    public static void main(String... args) {
        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("localhost", "candata", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            // SETUP INSTRUCTIONS:
            // Make sure your database is called candata, and you have a traces table premade
            // according to this command:
            // CREATE TABLE traces (trace_number INT DEFAULT NULL PRIMARY KEY, trace_name
            // varchar(50) DEFAULT NULL);

            // RUNNING INSTRUCTIONS:
            // Basically, what you do is for each trace make a trace object and insert that to the
            // "traces" table.
            // Then get the traceNum, which will be used to generate the rest of the tables. Then
            // for each signal, make
            // a corresponding signal table, and fill it using the Data class (should make that a
            // constructor I know but
            // it is 3 and I am tired. I assume as you do that you are filling your arraylist or
            // whatever for the bucket
            // cutoffs. Once the first is done, use the createKeyTable passing in the traceNum and
            // the size of one signal's
            // worth of cutoffs to generate the table, and then create a Key object and pass that
            // into the table.
            // ONLY MAKE THE TABLE ON THE FIRST KEY OBJECT SEEN. Then that is basically it.
            // rinse and repeat the process of making the signal table, passing in data objects and
            // inserting into the
            // key table as you go.

            ArrayList<Integer> bucketCutoffs = new ArrayList<Integer>();
            bucketCutoffs.add(1);
            bucketCutoffs.add(2);
            Key newKey = new Key("testSignal", bucketCutoffs);
            dataDAO newDAO = new dataDAO(connection);

            int traceNum = newDAO.getTraceNum();
            Trace trace = new Trace(traceNum, "testTrace");
            newDAO.insertTraceData(trace);
            newDAO.createKeyTable(traceNum, bucketCutoffs.size());

            newDAO.createSignalTable(traceNum, "testSignal");
            newDAO.insertKeyData(traceNum, newKey);
            Data newData = new Data(1, 1);
            newDAO.insertSignalData(traceNum, "testSignal", newData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
