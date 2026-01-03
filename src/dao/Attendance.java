package dao;

import database.MySqlConnection;
import model.Student;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

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
        Connection conn = mySql.openConnection();
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
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

    public boolean validateBatchAndModule(int batchId, int moduleId) {
        Connection conn = mySql.openConnection();

        try {
            // Step 1: check batch exists
            String batchSql = "SELECT 1 FROM Batch WHERE batchId = ? LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(batchSql)) {
                ps.setInt(1, batchId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Batch ID does not exist!",
                                "Validation Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return false; // stop here
                    }
                }
            }

            // Step 2: check module is assigned to batch
            String moduleSql = """
            SELECT 1
            FROM BatchTeacherModule btm
            INNER JOIN TeacherModule tm 
                ON btm.teacherModuleId = tm.teacherModuleId
            WHERE btm.batchId = ? AND tm.moduleId = ?
            LIMIT 1
        """;

            try (PreparedStatement ps = conn.prepareStatement(moduleSql)) {
                ps.setInt(1, batchId);
                ps.setInt(2, moduleId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "This module is NOT assigned to the selected batch!",
                                "Validation Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return false;
                    }
                }
            }

            // Both validations passed
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public ArrayList<Student> getBatchModuleAttendance(int batchId, int moduleId, java.util.Date date) {
        ArrayList<model.Student> attendanceList = new ArrayList<>();
        Connection conn = mySql.openConnection(); // your connection helper
        String sql = "{CALL GetBatchModuleAttendance(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, batchId);
            cs.setInt(2, moduleId);
            cs.setDate(3, new java.sql.Date(date.getTime())); // convert java.util.Date to java.sql.Date

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    int studentId = rs.getInt("studentId");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String status = rs.getString("attendanceStatus");

                    model.Student sa = new model.Student(studentId, firstName, lastName, status);
                    attendanceList.add(sa);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            mySql.closeConnection(conn);
        }

        return attendanceList;
    }




}
