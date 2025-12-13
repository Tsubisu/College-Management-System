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
    public void userSetup(User user)
    {
        Connection conn = mysql.openConnection();
        String sql = "Select * from users where email = ? and password = ?";
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getEmail());
            pstm.setString(2, user.getPassword());
            ResultSet result = pstm.executeQuery();
            result.next();
            user.setFirstName(result.getString(2));
            user.setLastName(result.getString(3));
            user.setAddress(result.getString(6));
            user.setContact(result.getString(7));
            user.setGender(result.getString(8));
            user.setRole(result.getString(9));

        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mysql.closeConnection(conn);
        }
    }
}
