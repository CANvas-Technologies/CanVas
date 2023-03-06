package org.canvas.server;

import java.sql.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    @GetMapping("/api")
    public String index() {
        return "api";
    }

    @GetMapping(value = "/api/get_trace_uuid/{name}", produces = "text/plain")
    @ResponseBody
    public String getTraceUUID(@PathVariable("name") String name) {
        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("db", "candata", "postgres", "password");

        String uuid = "";
        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);

            uuid = db.getTraceUUID(name);
            System.out.println("GOT UUID: " + uuid);
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }

        return uuid;
    }

    @GetMapping(value = "/api/get_signal_uuid/{trace_uuid}/{signal_name}", produces = "text/plain")
    @ResponseBody
    public String getTraceUUID(
            @PathVariable("trace_uuid") String traceUUID,
            @PathVariable("signal_name") String signalName) {
        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("db", "candata", "postgres", "password");

        String uuid = "";
        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);

            uuid = db.getSignalUUID(traceUUID, signalName);
            System.out.println("GOT SIGNAL UUID: " + uuid);
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }

        return uuid;
    }

    @GetMapping(
            value = "/api/get_data_by_bucket/{trace_uuid}/{signal_uuid}/{bucket}",
            produces = "text/plain")
    @ResponseBody
    public String getDataByBucket(
            @PathVariable("trace_uuid") String traceUUID,
            @PathVariable("signal_uuid") String signalUUID,
            @PathVariable("bucket") int bucket) {
        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("db", "candata", "postgres", "password");

        String dataInBucket = "";
        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);

            dataInBucket = db.getDataInBucket(traceUUID, signalUUID, bucket);
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }

        return dataInBucket;
    }

    @GetMapping(value = "/api/delete_trace/{trace_uuid}", produces = "text/plain")
    @ResponseBody
    public String deleteTrace(@PathVariable("trace_uuid") String traceUUID) {
        DatabaseConnectionManager dcm =
                new DatabaseConnectionManager("db", "candata", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            DatabaseDAO db = new DatabaseDAO(connection);

            return db.deleteTrace(traceUUID);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return "Failed to delete trace {" + traceUUID + "}.";
    }
}
