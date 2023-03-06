package org.canvas.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    }

    void createKeyTable(TraceHandle trace, int size) {
        // signal_name | signal_data_table | indices ... ||||||||
        // ------------|-------------------|-------------||||||||

        // signal_data_table name length is 48 characters - signal_UUID_data
        // e.g. signal_d8f35758-3610-47e5-9e11-b745d6154bf6_data
        StringBuilder temp =
                new StringBuilder()
                        .append("CREATE TABLE IF NOT EXISTS ")
                        .append(DatabaseDAO.wrapQuotes(trace.getKeyTableName()))
                        .append(" (signal_name varchar(1000) PRIMARY KEY," +
                                " signal_data_table char(48)");
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
                                .append("CREATE TABLE IF NOT EXISTS ")
                                .append(DatabaseDAO.wrapQuotes(sig.getDataTableName()))
                                .append(
                                        " (timestamp FLOAT(20) PRIMARY KEY,data"
                                                + " FLOAT(20))"))
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
                ((new StringBuilder()).append("INSERT INTO ")
                        .append(DatabaseDAO.wrapQuotes(sig.getDataTableName()))
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
                        .append(DatabaseDAO.wrapQuotes(trace.getKeyTableName()))
                        .append(" VALUES (")
                        .append(DatabaseDAO.wrapSingleQuotes(sigData.getName()))
                        .append(", " + DatabaseDAO.wrapSingleQuotes(sig.getDataTableName()));

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
}
