package org.canvas.server;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        Path mdf = Paths.get("../pythonjava/examples/audi/output1.mf4");
        Path dbc = Paths.get("../pythonjava/examples/audi/audi.dbc");
        File[] files = MdfImporter.convertMdfToCsvFiles(mdf, dbc);
        System.out.println(MdfImporter.readCsvFileToSignalData(files[1]));
    }
}
