package dao;

import database.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordUpdate {
    MySqlConnection mySql = MySqlConnection.getMySqlConnection();

    public void updatePassword(String email, String newPassword)
    {
        Connection conn = mySql.openConnection();
        String sql = "UPDATE users SET userPassword = ? WHERE email = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);
            System.out.println(sql);
            mySql.executeUpdate(conn, ps);

        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
    }
}
