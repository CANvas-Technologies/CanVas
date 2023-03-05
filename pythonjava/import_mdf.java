import java.nio.file.Path;
import java.nio.file.Paths;

import java.lang.Process;
import java.lang.ProcessBuilder;

public class import_mdf {
    public static void main(String[] args) throws Exception {
        Path mdf = Paths.get("./sdf");
        Path dbc = Paths.get("./sdsdsd");
        convertMdfToCsvFiles(mdf, dbc);
    }

    public static String[] convertMdfToCsvFiles(Path mf4, Path dbc) {
        try {
            Process mdfpy = new ProcessBuilder(
                "python3",
                "exportcsv.py",
                mf4.toAbsolutePath().toString(),
                dbc.toAbsolutePath().toString()
            ).start();

            // has the process exited successfully?
            if (mdfpy.waitFor() != 0) {
                // Dump stderr to a string and throw an exception.
                String err = new String(mdfpy.getErrorStream().readAllBytes());
                throw new Exception("exportcsv failed. stderr follows:-----\n" + err + "\n-----");
            }
        } catch (Exception e) {
            System.err.println("Caught exception: " + e.toString());
            System.exit(-1);
        }

        return null;
    }
}
