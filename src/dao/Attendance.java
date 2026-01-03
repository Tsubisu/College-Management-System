package dao;

import database.MySqlConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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


    public model.Attendance getStudentModuleAttendanceSummary(int studentId, int moduleId) {
        model.Attendance summary = null;
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = mySql.openConnection(); // your connection utility
            cs = conn.prepareCall("{CALL GetStudentModuleAttendanceSummary(?, ?)}");
            cs.setInt(1, studentId);
            cs.setInt(2, moduleId);

            rs = cs.executeQuery();
            if (rs.next()) {
                int present = rs.getInt("totalPresent");
                int absent = rs.getInt("totalAbsent");
                int total = rs.getInt("totalDays");
                summary = new model.Attendance(present, absent, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn); // make sure to close connection
        }

        return summary;
    }

}
