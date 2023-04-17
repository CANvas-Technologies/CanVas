package org.canvas.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {
    Connection connection;

    static String wrapQuotes(String s) {
        return "\"" + s + "\"";
    }

    static String wrapSingleQuotes(String s) {
        return "\'" + s + "\'";
    }

    public DatabaseDAO(Connection connection) {
        this.connection = connection;

        // Set up the primary traces table if it doesn't exist.
        final String createPrimaryTable =
                "CREATE TABLE IF NOT EXISTS traces (trace_uuid char(36) PRIMARY KEY, trace_name varchar(1000), trace_key_table char(47))";
        try (PreparedStatement statement = this.connection.prepareStatement(createPrimaryTable); ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    void createKeyTable(TraceHandle trace, int size) {
        // signal_name | signal_uuid | indices ... ||||||||
        // ------------|-------------|-------------||||||||
        StringBuilder temp =
                new StringBuilder()
                        .append("CREATE TABLE IF NOT EXISTS ")
                        .append(wrapQuotes(trace.getKeyTableName()))
                        .append(
                                " (signal_name varchar(1000) PRIMARY KEY,"
                                        + " signal_uuid char(36)");
        for (int i = 0; i < size; i++) {
            temp.append(", B" + i + " INT DEFAULT NULL");
        }
        temp.append(" )");
        final String createKeyTable = temp.toString();

        try (PreparedStatement statement = this.connection.prepareStatement(createKeyTable); ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    void createSignalTable(SignalHandle sig) {
        final String CREATE_SIGNAL_TABLE =
                ((new StringBuilder())
                                .append("CREATE TABLE IF NOT EXISTS")
                                .append(wrapQuotes(sig.getDataTableName()))
                                .append(" (timestamp FLOAT(20) PRIMARY KEY,data" + " FLOAT(20))"))
                        .toString();
        try (PreparedStatement statement =
                this.connection.prepareStatement(CREATE_SIGNAL_TABLE); ) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    void insertSignalData(SignalHandle sig, SignalData sigData) {
        final String INSERT_DATA =
                ((new StringBuilder())
                                .append("INSERT INTO ")
                                .append(wrapQuotes(sig.getDataTableName()))
                                .append(" VALUES(?,?)"))
                        .toString();

        List<SignalDatapoint> data = sigData.getData();
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_DATA); ) {
            int i = 0;
            for (SignalDatapoint d : data) {
                statement.setDouble(1, d.getTimestamp());
                statement.setDouble(2, d.getData());
                statement.addBatch();
                i++;

                if (i % 1000 == 0 || i == data.size()) {
                    statement.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    void insertKeyData(TraceHandle trace, SignalHandle sig, SignalData sigData) {
        List<Integer> cutoffs = sigData.getBucketCutoffs();

        StringBuilder temp =
                new StringBuilder()
                        .append("INSERT INTO ")
                        .append(wrapQuotes(trace.getKeyTableName()))
                        .append(" VALUES (")
                        .append(wrapSingleQuotes(sigData.getName()))
                        .append(", " + wrapSingleQuotes(sig.getUUIDString()));

        for (int i = 0; i < cutoffs.size(); i++) {
            temp.append(", ").append(cutoffs.get(i));
        }
        temp.append(")");
        final String insertKeyData = temp.toString();

        try (PreparedStatement statement = this.connection.prepareStatement(insertKeyData); ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public TraceHandle newTrace(String name) {
        TraceHandle trace = new TraceHandle(name);

        final String INSERT_TRACE = "INSERT INTO traces VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_TRACE); ) {
            statement.setString(1, trace.getUUIDString());
            statement.setString(2, trace.getName());
            statement.setString(3, trace.getKeyTableName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.createKeyTable(trace, 60 * 20); // hardcoded 20 minutes max

        return trace;
    }

    public SignalHandle newSignal(TraceHandle trace, SignalData sigData) {
        SignalHandle sig = new SignalHandle(trace, sigData.getName());
        this.insertKeyData(trace, sig, sigData);
        this.createSignalTable(sig);
        this.insertSignalData(sig, sigData);


        return sig;
    }

    // For now, trace names must be unique.
    public String getTraceUUID(String traceName) {
        String output = "";
        final String getUUID = "SELECT trace_uuid FROM traces WHERE trace_name =?";
        try (PreparedStatement statement = this.connection.prepareStatement(getUUID); ) {
            statement.setString(1, traceName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                output = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return output;
    }

    public String getSignalUUID(String traceUUID, String signalName) {
        String name = "trace_" + traceUUID + "_keys";
        String output = "";
        final String GET_TABLE_NAME = "SELECT signal_uuid FROM " + wrapQuotes(name) + " WHERE signal_name = " + wrapSingleQuotes(signalName);
        try (PreparedStatement statement = this.connection.prepareStatement(GET_TABLE_NAME); ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                output = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return output;
    }

    public ArrayList<Integer> getBucketCutoffs(String traceUUID, String signalUUID, int bucketVal) {
        ArrayList<Integer> bucketVals = new ArrayList<Integer>();
        StringBuilder bottomTemp = new StringBuilder();
        if (bucketVal <= 0) {
            bucketVals.add(0);
        } else {
            bottomTemp
                    .append("SELECT b")
                    .append(bucketVal - 1)
                    .append(" FROM ")
                    .append(wrapQuotes("trace_" + traceUUID + "_keys"))
                    .append(" WHERE signal_uuid = ")
                    .append(wrapSingleQuotes(signalUUID));
            final String BOTTOM_COMMAND = bottomTemp.toString();
            try (PreparedStatement statement = this.connection.prepareStatement(BOTTOM_COMMAND); ) {

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    bucketVals.add(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        StringBuilder topTemp = new StringBuilder();
        topTemp.append("SELECT b")
                .append(bucketVal)
                .append(" FROM ")
                .append(wrapQuotes("trace_" + traceUUID + "_keys"))
                .append(" WHERE signal_uuid = ")
                .append(wrapSingleQuotes(signalUUID));
        final String TOP_COMMAND = topTemp.toString();
        try (PreparedStatement statement = this.connection.prepareStatement(TOP_COMMAND); ) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bucketVals.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bucketVals;
    }
    public ArrayList<String> getSignalNames(String traceUUID){
        ArrayList<String> stringNames = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        temp.append("SELECT signal_name FROM ").append(wrapQuotes("trace_" + traceUUID + "_keys"));
        final String GET_SIGNAL_NAMES = temp.toString();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_SIGNAL_NAMES); ) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringNames.add(resultSet.getString("signal_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return stringNames;
    }

    public String getDataInBucket(String traceName, String stringName, int bucketVal) {
        String output = "";
        String traceUUID = getTraceUUID(traceName);
        System.out.println(traceUUID);
        //System.out.println("GOT TRACE UUID:" + traceUUID);
        String signalUUID = getSignalUUID(traceUUID, stringName);
        System.out.println(signalUUID);
        StringBuilder temp = new StringBuilder();
        List<Integer> bucketBounds = getBucketCutoffs(traceUUID, signalUUID, bucketVal);
        temp.append("SELECT * FROM ")
                .append("( SELECT timestamp, data, ROW_NUMBER () OVER (ORDER BY timestamp) ")
                .append("FROM ")
                .append(wrapQuotes("signal_" + signalUUID + "_data"))
                .append(") x WHERE ROW_NUMBER BETWEEN ")
                .append(bucketBounds.get(0))
                .append(" AND ")
                .append(bucketBounds.get(1));
                /*.append(wrapQuotes("signal_" + signalUUID + "_data"))
                .append(" WHERE timestamp > ")
                .append(bucketBounds.get(0))
                .append(" AND timestamp <= ")
                .append(bucketBounds.get(1));*/
        final String RETRIEVE_COMMAND = temp.toString();
        try (PreparedStatement statement = this.connection.prepareStatement(RETRIEVE_COMMAND)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // System.out.println(resultSet.getInt(1));
                output =
                        output
                                + "timestamp:"
                                + resultSet.getFloat("timestamp")
                                + " data point: "
                                + resultSet.getFloat("data")
                                + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return output;
    }

    public void dropThatTable(String tableName) {
        final String DROP_COMMAND = "DROP TABLE " + wrapQuotes(tableName);
        try (PreparedStatement statement = this.connection.prepareStatement(DROP_COMMAND); ) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String deleteTrace(String traceName) {
        String traceUUID = getTraceUUID(traceName);
        ArrayList<String> tableNames = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        temp.append("SELECT signal_uuid FROM ").append(traceUUID).append("_keys");
        final String GET_NAMES = temp.toString();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_NAMES); ) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // System.out.println(resultSet.getInt(1));
                tableNames.add(resultSet.getString("signal_data_table"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        for (int i = 0; i < tableNames.size(); i++) {
            dropThatTable(tableNames.get(i));
        }
        dropThatTable(traceUUID + "_keys");
        final String REMOVE_TRACE_COMMAND =
                "DELETE FROM traces WHERE trace_uuid = " + wrapSingleQuotes(traceUUID);
        try (PreparedStatement statement =
                this.connection.prepareStatement(REMOVE_TRACE_COMMAND); ) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // final String getNames = temp.toString()
        return "ALL TABLES DROPPED, TRACE INSTANCE REMOVED";
    }

    public int getBucketCount(String traceName, String signalName){
        String traceUUID = getTraceUUID(traceName);
        System.out.println(traceUUID);
        String signalUUID = getSignalUUID(traceUUID,signalName);
        System.out.println(signalUUID);
        int count = 0;

        for (int i=0; i<1200; i++){
            List<Integer> bucketBounds = getBucketCutoffs(traceUUID, signalUUID, i);
            //System.out.println(bucketBounds);
            if(i == 0){
                count = count + 1;
            }
            else{
                StringBuilder temp = new StringBuilder();
                /*temp.append("SELECT timestamp").append(" FROM ").append(wrapQuotes("signal_" + signalUUID + "_data"))
                        .append(" WHERE timestamp >= " + bucketBounds.get(0))
                        .append(" AND timestamp <= " + bucketBounds.get(1));*/
                temp.append("SELECT * FROM")
                        .append("( SELECT timestamp, ROW_NUMBER () OVER (ORDER BY timestamp) ")
                        .append(" FROM")
                        .append(wrapQuotes("signal_" + signalUUID + "_data"))
                        .append(" )x")
                        .append(" WHERE ROW_NUMBER = ")
                        .append(bucketBounds.get(1));
                final String GET_COUNT = temp.toString();
                try (PreparedStatement statement = this.connection.prepareStatement(GET_COUNT, ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE); ) {

                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next()){
                        if(resultSet.getInt(1) !=0){
                            count = count +1;
                            System.out.println(resultSet.getFloat(1));
                        }


                        resultSet.beforeFirst();

                    }



                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
          /*  StringBuilder temp = new StringBuilder();
            temp.append("SELECT SUM(CASE WHEN \'b" + i + "\' IS NULL THEN 0 ELSE 1 END) FROM ").append(wrapQuotes("trace_" + traceUUID + "_keys"))
                    .append(" WHERE signal_name = ").append(wrapSingleQuotes(signalName));
            final String GET_COUNT = temp.toString(); */


        }

        return count;
    }
}
