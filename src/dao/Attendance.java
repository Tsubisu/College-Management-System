package dao;

import database.MySqlConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Attendance {

    private MySqlConnection mySql = MySqlConnection.getMySqlConnection();

    public void markAttendance(int studentId, int moduleId, int batchId, java.util.Date date, String status) {
        Connection conn = mySql.openConnection();
        String sql = "{CALL MarkAttendance(?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, studentId);
            cs.setInt(2, moduleId);
            cs.setInt(3, batchId);
            cs.setDate(4, new java.sql.Date(date.getTime())); // convert java.util.Date to java.sql.Date
            cs.setString(5, status); // "Present", "Absent", or "Excused"

            cs.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

}
