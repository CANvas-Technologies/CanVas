package org.example;

import java.sql.*;

public abstract class DataAccessObject<T extends DataTransferObject> {

    protected final Connection connection;
    protected static final String LAST_VAL = "SELECT last_value FROM ";
    protected static final String PLAYER_SEQUENCE = "player_seq";

    public DataAccessObject(Connection connection) {
        super();
        this.connection = connection;
    }

    // public abstract T findById(long id);
    public abstract void createKeyTable(int traceCount);

    public abstract void createSignalTable(int traceCount, String signalName);

    public abstract Data setDataVal(long timestamp, long data);

    public abstract void insertSignalData(Data data);
}
