package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataAccessObject <E extends DataTransferObject>  {

    protected final Connection connection;


    public DataAccessObject(Connection connection){
        super();
        this.connection = connection;
    }

   // public abstract T findById(long id);
   public abstract void createKeyTable(int traceNum, int size);
    public abstract void createSignalTable(int traceCount,String signalName);
    public abstract Data setDataVal(long timestamp, long data);
    public abstract void insertSignalData(int traceCount, String signalName,Data data);

    public abstract void insertKeyData(int traceCount, Key smallKey );

    public abstract int getTraceNum();

    public abstract void insertTraceData(Trace trace);

    public abstract void retrieveData(int traceNum, String signalName,int bucket );

    public abstract ArrayList<Integer> getBucketVals(int traceNum, String name, int bucket );


    public abstract void deleteData(int traceNum, String signalName, long timestamp );


}