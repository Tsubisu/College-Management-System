package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
public class MySqlConnection implements Database{

    private static MySqlConnection mySqlConnection;
    private MySqlConnection(){}

    public static MySqlConnection getMySqlConnection()
    {
        if (mySqlConnection== null){
            mySqlConnection= new MySqlConnection();
        }
        return mySqlConnection;
    }

    @Override
    public Connection openConnection() {
        try{
            String username = "root";
            String password = "Tsubisu3#";
            String database = "CSM";
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+database,username,password);
            if(connection == null){
                System.out.println("Database connection fail");
            }
            else{
               // System.out.println("Database connection success");
            }
            return connection;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
               // System.out.println("Connection close");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, PreparedStatement pstm) {
        try{
            return pstm.executeQuery();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }


    public int executeUpdate(Connection conn, PreparedStatement pstm) throws SQLException {
        return pstm.executeUpdate();
    }

}