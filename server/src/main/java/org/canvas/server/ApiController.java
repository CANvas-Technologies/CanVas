package org.canvas.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    DatabaseDAO db = DatabaseDAO.LocalDatabase();

    @GetMapping("/api")
    public String index() {
        return "api";
    }

    @GetMapping(value = "/api/get_trace_uuid/{name}", produces = "text/plain")
    @ResponseBody
    public String getTraceUUID(@PathVariable String name) {
        return db.getTraceUUID(name);
    }

    @GetMapping(value = "/api/get_signal_uuid/{traceUUID}/{signalName}", produces = "text/plain")
    @ResponseBody
    public String getTraceUUID(@PathVariable String traceUUID, @PathVariable String signalName) {
        return db.getSignalUUID(traceUUID, signalName);
    }

    @GetMapping(
            value = "/api/get_data_by_bucket/{traceUUID}/{signalUUID}/{bucket}",
            produces = "text/plain")
    @ResponseBody
    public String getDataByBucket(
            @PathVariable String traceUUID,
            @PathVariable String signalUUID,
            @PathVariable int bucket) {
        return db.getDataInBucket(traceUUID, signalUUID, bucket);
    }

    @GetMapping(value = "/api/delete_trace/{traceUUID}", produces = "text/plain")
    @ResponseBody
    public String deleteTrace(@PathVariable String traceUUID) {
        try {
            return db.deleteTrace(traceUUID);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return "Failed to delete trace {" + traceUUID + "}.";
    }
}
