package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dataDAO extends DataAccessObject{


    private static final String ADD_TRACE= "INSERT INTO traces" + " VALUES(?,?)";
    //private static final String CREATE_KEY_TABLE = "CREATE TABLE" + ? + "(B0 INT DEFAULT NULL)";



    public dataDAO(Connection connection) {
        super(connection);
    }
    @Override


    public void createKeyTable(int traceNum, int size){
        String name = "keys" + traceNum;


        StringBuilder temp = new StringBuilder();
        temp.append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (signal_name varchar(50) DEFAULT NULL PRIMARY KEY");
        for(int i =0; i <size; i++){
            temp.append(", B" + i + " INT DEFAULT NULL");
        }
        temp.append(" )");
        final String CREATE_KEY_TABLE = temp.toString();
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
        final String CREATE_SIGNAL_TABLE = ((new StringBuilder()).append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (timestamp FLOAT(20) DEFAULT NULL PRIMARY KEY,data FLOAT(20) DEFAULT NULL)")).toString();
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_SIGNAL_TABLE);){

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Data setDataVal(long timestamp, long data){
        Data newData = new Data();
        newData.setData(data);
        newData.setTimestamp(timestamp);
        return newData;
    }
    public void insertSignalData(int traceCount, String signalName, Data data){
         String name = signalName + traceCount;
         final String INSERT_DATA = ((new StringBuilder()).append("INSERT INTO ").append(name).append(" VALUES(?,?)")).toString();
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT_DATA);){
            statement.setLong(1,data.getTime());
            statement.setLong(2,data.getData());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public void insertKeyData(int traceCount, Key smallKey ){
        String name  = smallKey.getSignalName().toLowerCase() +  traceCount;
        StringBuilder temp  = new StringBuilder();
        temp.append("INSERT INTO ").append("keys"+traceCount).append(" VALUES (").append("\'").append(name).append("\'");

        for (int i  =0; i<smallKey.getBucketCutoff().size(); i++){

            temp.append(", ").append(smallKey.getBucketCutoff().get(i));

        }
        temp.append(")");
        final String INSERT_KEY_DATA = temp.toString();
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT_KEY_DATA);){

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
    public int getTraceNum(){
        try(PreparedStatement statement = this.connection.prepareStatement("SELECT COUNT(*) FROM traces");){
            int output=10;
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                output = resultSet.getInt(1);
            }
            return output;

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void insertTraceData(Trace trace){
        final String INSERT_TRACE = "INSERT INTO traces VALUES (?,?)";
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT_TRACE);){
            statement.setInt(1,trace.getId());
            statement.setString(2,trace.getName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Integer> getBucketVals(int traceNum, String name, int bucket){
        ArrayList<Integer> bucketVals= new ArrayList<Integer>();
        StringBuilder bottomTemp = new StringBuilder();
        bottomTemp.append("SELECT ").append("b").append(bucket-1).append(" FROM ").append("keys").append(traceNum).append(" WHERE signal_name=").append("\'").append(name).append("\'");
        String bottomCommand = bottomTemp.toString();
        if(bucket<=0){
            bucketVals.add(0);
        }
        else{
            try(PreparedStatement statement = this.connection.prepareStatement(bottomCommand)){

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    bucketVals.add(resultSet.getInt(1));
                }


            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        StringBuilder topTemp = new StringBuilder();
        topTemp.append("SELECT ").append("b").append(bucket).append(" FROM ").append("keys").append(traceNum).append(" WHERE signal_name=").append("\'").append(name).append("\'");
        String topCommand = topTemp.toString();
        try(PreparedStatement statement = this.connection.prepareStatement(topCommand)){

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
               bucketVals.add(resultSet.getInt(1));
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bucketVals;
    }

    public void retrieveData(int traceNum, String signalName, int bucket){
        String name = signalName + traceNum;
        StringBuilder temp = new StringBuilder();
        List<Integer> bucketBounds = getBucketVals(traceNum,name,bucket);

        temp.append("SELECT * FROM ").append(name).append(" WHERE timestamp >").append(bucketBounds.get(0)).append(" AND timestamp<=").append(bucketBounds.get(1));
        String retrieveCommand = temp.toString();
        try(PreparedStatement statement = this.connection.prepareStatement(retrieveCommand)){

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteData(int traceNum, String signalName, long timestamp){
        String name = signalName + traceNum;
        String DELETE_DATA = ((new StringBuilder()).append("DELETE FROM ").append(name).append(" WHERE timestamp =").append(timestamp)).toString();
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE_DATA);){


            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}