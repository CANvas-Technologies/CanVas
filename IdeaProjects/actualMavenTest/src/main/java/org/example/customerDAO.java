package org.example;
import org.example.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class customerDAO extends DataAccessObject{

    private static final String GET_ONE = "SELECT customer_id, first_name, last_name " +
            "FROM customer WHERE customer_id=?";
    private static final String ADD_TRACE= "INSERT INTO traces" + " VALUES(?,?)";
    //private static final String CREATE_KEY_TABLE = "CREATE TABLE" + ? + "(B0 INT DEFAULT NULL)";



    public customerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                customer.setCustomerId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }
    @Override
    public void createKeyTable(int traceCount){

        String name = "keys" + traceCount;
        final String CREATE_KEY_TABLE = ((new StringBuilder()).append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (string_name varchar(50) DEFAULT NULL, B0 INT DEFAULT NULL)")).toString();
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_KEY_TABLE);){

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public void createSignalTable(int traceCount, String signalName){
        String name = signalName + traceCount;
        final String CREATE_SIGNAL_TABLE = ((new StringBuilder()).append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (timestamp INT DEFAULT NULL,data FLOAT(20) DEFAULT NULL)")).toString();
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_SIGNAL_TABLE);){

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}