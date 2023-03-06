package org.canvas.server;

import java.util.UUID;

public class TraceHandle {
    public TraceHandle(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.keyTableName = "trace_" + this.getUUIDString() + "_keys";
    }

    private UUID uuid;
    private String name;
    private String keyTableName;

    public UUID getUUID() {
        return this.uuid;
    }

    public String getUUIDString() {
        return this.getUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getKeyTableName() {
        return this.keyTableName;
    }
}
