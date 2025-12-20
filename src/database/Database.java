package database;
import java.sql.*;
public interface Database {
    Connection openConnection();

    void closeConnection(Connection conn);

    ResultSet runQuery(Connection conn, PreparedStatement pstm);

    int executeUpdate(Connection conn, PreparedStatement pstm);
}