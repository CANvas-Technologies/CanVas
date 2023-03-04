package javapart;
import java.util.*;
import java.io.*;

public class testjava {
    public static void main(String[] args) {
        //Executes the python command and dumps the output csv files into the directory
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter MF4 file");
        String input_mf4 = myObj.nextLine();  // Read user input
        System.out.println("Enter DBC file");
        String input_dbc = myObj.nextLine();

        try{
            String command;
            Process p = Runtime.getRuntime().exec("python3 pythontest.py"+ " "+input_mf4+" "+input_dbc);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            String ret2 = in.readLine();
            System.out.println("value is : " + ret);
            System.out.println("value is : " + ret2);

            //Process p = Runtime.getRuntime().exec("python3 pythontest.py");
        }catch(Exception e){}

        File dir = new File("C:\\Users\\seren_rmybriy\\IdeaProjects\\pythonjava\\src\\javapart");
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept (File dir, String name) {
            return name.startsWith("out.");
        }
        };
        String[] children = dir.list(filter);
        if (children == null) {
            System.out.println("Either dir does not exist or is not a directory");
        } else {
            for (int i = 0; i< children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
            }
        }
    }
}



