package dao;

import java.sql.*;

import java.sql.Connection;
import database.MySqlConnection;

public class BatchDao {
    private MySqlConnection mySql = MySqlConnection.getMySqlConnection();

    public boolean addBatch(model.Batch batch) {
        if (batch == null) return false;

        Connection conn = mySql.openConnection();
        String sql = "INSERT INTO Batch (batch_name, section, courseId, courseYear, semester) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, batch.getBatchName());
            ps.setString(2, String.valueOf(batch.getSection()));
            ps.setInt(3, batch.getCourseId());
            ps.setInt(4, batch.getCourseYear()); // make sure you have this field in your model
            ps.setInt(5, batch.getSemester());   // make sure you have this field in your model

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

}
