package org.canvas.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.sql.Connection;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;


@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class GraphingController {
    @GetMapping("/graphing")
    public String index() {
        return "graphing";
    }

    //@GetMapping(value = "/graphing/getSignalData/{traceName}/{signalName}", produces = "text/plain" )

    @CrossOrigin
    @RequestMapping(value = "/graphing/getSignalData/{traceName}/{signalName}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public String getSignalData(@PathVariable("traceName") String traceName, @PathVariable("signalName") String signalName){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "candata", "postgres", "password");

        String output = "TESTING";
        String[] toInsert;
        String[] blank = new String[] { "" };
        JSONArray values = new JSONArray();
        JSONArray nullVals = new JSONArray();
        String[] finalArray;
        List<String> exampleList = new ArrayList<String>();
        List<String> blankList = new ArrayList<String>();
        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);
            int bucketCount = db.getBucketCount(traceName);
            System.out.println(bucketCount);
            //JSONObject values = new JSONObject();
            for (int i = 0; i<bucketCount; i++){
                output = db.getDataInBucket( traceName,  signalName, i);

                toInsert = output.split("\n");

                for (String element: toInsert){
                    String[] tempList =element.split(" ", 2);
                    System.out.println(tempList[0]);
                    if(tempList[0] != ""){
                        String tempTime = (tempList[0].split(":",2))[1];
                        System.out.println(tempTime);
                        //System.out.println(tempList[0]);
                        String tempData = (tempList[1].split(" ",3))[2];
                        System.out.println(tempData);
                        JSONObject tempValues = new JSONObject();
                        tempValues.put("timestamp", tempTime);
                        tempValues.put("data point", tempData);
                        values.put(tempValues);
                    }


                }

            }

        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
        //System.out.println("Normal return here");
        //System.out.println(values);
        return values.toString();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/graphing/getTrace/{traceName}",produces = "text/plain")
    @ResponseBody
    public String getTrace(@PathVariable("traceName") String traceName){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "candata", "postgres", "password");
        String output = "";

        try{
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);
            int bucketCount = db.getBucketCount(traceName);
            System.out.println(bucketCount);
            output = output + bucketCount;
        }catch (Throwable e) {
            e.printStackTrace();
            return "";
        }

        return output;
    }


}
