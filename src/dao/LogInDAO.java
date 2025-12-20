package dao;
import database.MySqlConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LogInDAO {
    MySqlConnection mysql = MySqlConnection.getMySqlConnection();



    public boolean userCheck(String email)
    {
        Connection conn = mysql.openConnection();
        String sql = "Select * from users where email = ?";
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            ResultSet result = mysql.runQuery(conn, pstm);
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


    public boolean loginCheck(String email, String password) {
        Connection conn = mysql.openConnection();
        String sql = "Select * from users where email = ? and password = ?";
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet result = mysql.runQuery(conn, pstm);
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
    public String getCurrentPassword(String email)
    {

        Connection conn = mysql.openConnection();
        String sql = "Select userPassword from users where email = ?";
        String currentPassword="";
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            ResultSet result = mysql.runQuery(conn,pstm);
            result.next();
            currentPassword=result.getString(1);
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mysql.closeConnection(conn);
        }
        return currentPassword;
    }
    public void getUserDetail(User user)
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
