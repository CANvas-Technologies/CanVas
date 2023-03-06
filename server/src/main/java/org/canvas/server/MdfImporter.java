package org.canvas.server;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class MdfImporter {
    // read in signal data, not bucketized
    public static SignalData readCsvFileToSignalData(File file) throws Exception {
        try {
            // make CSV reader
            CSVReader csv = new CSVReader(new FileReader(file));

            // for errors
            String path = "('" + file.getAbsolutePath() + "')";

            String[] header = null;

            if ((header = csv.readNext()) == null) {
                csv.close();
                throw new Exception("Expected at least one line in signal csv " + path);
            }

            if (header.length > 2) {
                // skip this file
                // todo: handle multiple signals per channel group
                System.err.println("Skipping " + file.getAbsolutePath());
                return null;
            }

            if (header.length < 2) {
                csv.close();
                throw new Exception("Header in csv file is too short " + path);
            }

            if (!header[0].equals("timestamps")) {
                csv.close();
                throw new Exception(
                        "First column should be 'timestamps' but is actually "
                                + header[0]
                                + " "
                                + path);
            }

            String signalName = header[1];

            System.out.println("Processing " + signalName);

            // todo: for now, all signal values are doubles, but there can actually be different
            // kinds
            List<Data> vals = new ArrayList<Data>();

            String[] line;
            while ((line = csv.readNext()) != null) {
                double timestamp = Double.parseDouble(line[0]);
                double value = Double.parseDouble(line[1]);
                vals.add(new Data(timestamp, value));
            }

            csv.close();
            return new SignalData(signalName, vals);
        } catch (Exception e) {
            throw e;
        }
    }

    public static File[] convertMdfToCsvFiles(Path mf4, Path dbc) throws Exception {
        // call python to convert mdf to .csv files
        Process mdfpy =
                new ProcessBuilder(
                                "python3",
                                "../pythonjava/exportcsv.py",
                                mf4.toAbsolutePath().toString(),
                                dbc.toAbsolutePath().toString())
                        .start();

        // has the process exited successfully?
        // todo: add timeout
        if (mdfpy.waitFor() != 0) {
            // Dump stderr to a string and throw an exception.
            String err = new String(mdfpy.getErrorStream().readAllBytes());
            throw new Exception("exportcsv failed. stderr follows:-----\n" + err + "\n-----");
        }

        // get the directory name from stdout
        String dir_name = new String(mdfpy.getInputStream().readAllBytes());
        dir_name = dir_name.trim(); // Remove trailing newline

        File dir = new File("./" + dir_name);

        // get csv file names in directory
        File[] files = dir.listFiles();
        if (files == null) {
            throw new Exception(
                    "Couldn't list in directory mdfpy should have made ('" + dir_name + "')");
        }

        return files;
    }
}
