package org.canvas.import_mdf;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MdfImporter {
    public static void main(String[] args) throws Exception {
        Path mdf = Paths.get("./examples/audi/output1.mf4");
        Path dbc = Paths.get("./examples/audi/audi.dbc");
        System.out.println(convertMdfToCsvFiles(mdf, dbc));
    }

    public static List<String> convertMdfToCsvFiles(Path mf4, Path dbc) throws Exception {
        // call python to convert mdf to .csv files
        Process mdfpy =
                new ProcessBuilder(
                                "python3",
                                "exportcsv.py",
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

        // get absolute path of each file
        List<String> paths = new ArrayList<String>();
        for (File f : files) {
            paths.add(f.getAbsolutePath());
        }

        return paths;
    }
}
