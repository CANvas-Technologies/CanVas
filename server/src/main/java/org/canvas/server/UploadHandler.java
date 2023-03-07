package org.canvas.server;

import java.io.File;
import java.nio.file.Path;

public class UploadHandler {
    static DatabaseDAO db = DatabaseDAO.LocalDatabase();

    public static TraceHandle HandleUpload(Path input) throws Throwable {
        TraceHandle trace = null;

        // pass input for both mf4 and dbc; asammdf handles it okay.
        File[] files = MdfImporter.convertMdfToCsvFiles(input, input);

        try {
            // SETUP INSTRUCTIONS:
            // Make sure your database is called candata, and you have a traces table premade
            // according to this command:

            // trace_uuid length is 36 (uuid4 length with separator hyphens)
            // trace_key_table name length is 47 chars - trace_UUID_keys, e.g.
            // trace_9d01a158-88b2-46d4-bb97-e29f5a4d6c09_keys
            //
            // CREATE TABLE traces (trace_uuid char(36) PRIMARY KEY, trace_name varchar(1000),
            // trace_key_table char(47));

            trace = db.newTrace("actual_real_trace");

            for (File f : files) {
                SignalData sigData = MdfImporter.readCsvFileToSignalData(f);

                if (sigData == null) { // this file was skipped
                    continue;
                }

                SignalHandle sig = db.newSignal(trace, sigData);
                System.out.println("Added signal with UUID " + sig.getUUIDString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trace;
    }
}
