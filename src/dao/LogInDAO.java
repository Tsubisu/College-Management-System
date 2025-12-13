package dao;
import database.MySqlConnection;
import mode.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LogInDAO {
    MySqlConnection mysql = new MySqlConnection();

    public boolean loginCheck(String email, String password) {
        Connection conn = mysql.openConnection();
        String sql = "Select * from users where email = ? and password = ?";
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet result = pstm.executeQuery();
            return result.next();
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
}
