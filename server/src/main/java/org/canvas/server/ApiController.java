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
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "candata", "postgres", "password");

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
    public String getTraceUUID(@PathVariable("trace_uuid") String traceUUID, @PathVariable("signal_name") String signalName) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "candata", "postgres", "password");

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
}
