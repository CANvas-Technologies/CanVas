package org.canvas.server;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://canvas.opencan.org:3000")
@Controller
public class GraphingController {
    static DatabaseDAO db = DatabaseDAO.LocalDatabase();

    @GetMapping("/graphing")
    public String index() {
        return "graphing";
    }

    @CrossOrigin(origins = "http://canvas.opencan.org:3000")
    @RequestMapping(value = "/graphing/getSignalData/{traceName}/{signalName}/{lowerBucket}/{upperBucket}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getSignalData(
            @PathVariable("traceName") String traceName,
            @PathVariable("signalName") String signalName,
            @PathVariable("lowerBucket") String lowerBucket,
            @PathVariable("upperBucket") String upperBucket) {

        String output = "TESTING";
        String[] toInsert;
        String[] blank = new String[] { "" };
        JSONArray values = new JSONArray();
        JSONArray nullVals = new JSONArray();
        int lower = Integer.parseInt(lowerBucket);
        int upper = Integer.parseInt(upperBucket);
        String[] finalArray;
        List<String> exampleList = new ArrayList<String>();
        List<String> blankList = new ArrayList<String>();
        try {
            for (int i = lower; i < upper; i++) {
                output = db.getDataInBucket(traceName, signalName, i);

                toInsert = output.split("\n");

                for (String element : toInsert) {
                    String[] tempList = element.split(" ", 2);
                    if (tempList[0] != "") {
                        String tempTime = (tempList[0].split(":", 2))[1];
                        String tempData = (tempList[1].split(" ", 3))[2];
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
        return values.toString();
    }

    @CrossOrigin(origins = "http://canvas.opencan.org:3000")
    @RequestMapping(value = "/graphing/getTrace/{traceName}/{signalName}", produces = "text/plain")
    @ResponseBody
    public String getTrace(
            @PathVariable("traceName") String traceName,
            @PathVariable("signalName") String signalName) {
        String output = "";

        try {
            int bucketCount = db.getBucketCount(traceName, signalName);
            output = output + bucketCount;
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }

        return output;
    }

    @CrossOrigin(origins = "http://canvas.opencan.org:3000")
    @RequestMapping(value = "/graphing/getSignalNames/{traceName}")
    @ResponseBody
    public ArrayList<String> getSignalNames(@PathVariable("traceName") String traceName) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> blank = new ArrayList<String>();
        try {
            String trace_uuid = db.getTraceUUID(traceName);
            output = db.getSignalNames(trace_uuid);
        } catch (Throwable e) {
            e.printStackTrace();
            return blank;
        }

        return output;
    }

    @CrossOrigin(origins = "http://canvas.opencan.org:3000")
    @RequestMapping(value = "/graphing/getTraceNames/{email}")
    @ResponseBody
    public ArrayList<String> getTraceName(@PathVariable("email") String email) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> blank = new ArrayList<String>();
        try {
            output = db.getTraceNames(email);

        } catch (Throwable e) {
            e.printStackTrace();
            return blank;
        }

        return output;
    }
}
