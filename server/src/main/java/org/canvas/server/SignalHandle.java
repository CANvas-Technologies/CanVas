package org.canvas.server;

import java.util.UUID;

public class SignalHandle {
    public SignalHandle(TraceHandle trace, String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.trace = trace;
    }

    private UUID id;
    private String name;
    private TraceHandle trace;

    public UUID getUUID() {
        return this.id;
    }

    public String getUUIDString() {
        return this.getUUID().toString();
    }

    public String getDataTableName() {
        return "signal_" + this.getUUIDString() + "_data";
    }
}
