package org.canvas.server;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class JDBCExecutor {

    public static void main(String... args) throws Throwable {
        Path mdf = Paths.get("../pythonjava/examples/audi/output1.mf4");
        Path dbc = Paths.get("../pythonjava/examples/audi/audi.dbc");
        File[] files = MdfImporter.convertMdfToCsvFiles(mdf, dbc);

        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("localhost", "candata", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO newDAO = new DatabaseDAO(connection);

            // SETUP INSTRUCTIONS:
            // Make sure your database is called candata, and you have a traces table premade
            // according to this command:
            // CREATE TABLE traces (trace_number INT DEFAULT NULL PRIMARY KEY, trace_name
            // varchar(50) DEFAULT NULL);

            TraceHandle trace = newDAO.newTrace("actual_real_trace");

            for (File f : files) {
                SignalData sigData = MdfImporter.readCsvFileToSignalData(f);

                if (sigData == null) { // this file was skipped
                    continue;
                }

                SignalHandle sig = newDAO.newSignal(trace, sigData);
                System.out.println("Added signal with UUID " + sig.getUUIDString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
