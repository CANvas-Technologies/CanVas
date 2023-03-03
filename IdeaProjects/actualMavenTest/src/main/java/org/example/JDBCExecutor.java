package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {


    public static void main(String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "candata", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
            while(resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
            dataDAO newDAO = new dataDAO(connection);
            newDAO.createKeyTable(5);
            newDAO.createSignalTable(9,"testSignal");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}