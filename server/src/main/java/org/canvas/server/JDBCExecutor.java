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

            // get future trace number
            final int traceNum = newDAO.getTraceNum();
            Trace trace = new Trace(traceNum, "actual_real_trace");
            newDAO.insertTraceData(trace);
            newDAO.createKeyTable(
                    traceNum,
                    60 * 20); // Hardcoded max size of 20 minutes, psql tables can have max
            // 1600 cols

            for (File f : files) {
                SignalData sig = MdfImporter.readCsvFileToSignalData(f);

                if (sig == null) { // this file was skipped
                    continue;
                }

                SignalKeyEntry key = new SignalKeyEntry(sig.getName(), sig.getBucketCutoffs());
                newDAO.insertKeyData(traceNum, key);

                newDAO.createSignalTable(traceNum, sig.getName());
                newDAO.insertSignalData(traceNum, sig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
