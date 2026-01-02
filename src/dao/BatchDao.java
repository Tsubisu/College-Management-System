package dao;

import java.sql.*;

import java.sql.Connection;
import java.util.ArrayList;

import database.MySqlConnection;
import model.Batch;

public class BatchDao {
    private MySqlConnection mySql = MySqlConnection.getMySqlConnection();

    public boolean addBatch(model.Batch batch) {
        if (batch == null) return false;

        Connection conn = mySql.openConnection();
        String sql = "CALL addBatch(?,?,?,?,?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1,batch.getBatchName());
            cs.setString(2,String.valueOf(batch.getSection()));
            cs.setInt(3,batch.getCourseId());
            cs.setInt(4,batch.getCourseYear());
            cs.setInt(5,batch.getSemester());
            mySql.executeUpdate(conn,cs);
            return true;

        } catch (SQLException e) {
            String errorMessage = e.getMessage();

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    errorMessage,
                    "Batch Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deleteBatchById(int batchId) {
        Connection conn = mySql.openConnection();
        boolean success = false;

        try {
            String sql = "{CALL DeleteBatchById(?)}";
            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, batchId);
                mySql.executeUpdate(conn,cs);
                success = true;
            }
        } catch (Exception e) {
            System.out.println("Error deleting batch: " + e);
        } finally {
            mySql.closeConnection(conn);
        }

        return success;
    }



    public ArrayList<model.Batch> getBatchByCourseId(int courseId) {


        Connection conn = mySql.openConnection();
        String sql = "CALL GetBatchesByCourse(?)";
        ArrayList<model.Batch> batches= new ArrayList<>();

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1,courseId);  // make sure you have this field in your model
            ResultSet resultSet= mySql.runQuery(conn,cs);
            while(resultSet.next()){
                int batchId=resultSet.getInt("batchId");
                String batchName= resultSet.getString("batch_Name");
                char section= resultSet.getString("section").charAt(0);
                int courseYear=resultSet.getInt("courseYear");
                int semester=resultSet.getInt("semester");
                String routinePdfPath=resultSet.getString("routinePdfPath");
                model.Batch batch = new Batch(batchId,batchName,section,courseYear,semester);
                batch.setRoutinePdfPath(routinePdfPath);
                batches.add(batch);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return batches;
    }


    public model.Teacher getAssignedTeacherForBatchModule(int batchId, int moduleId) {

        Connection conn = mySql.openConnection();
        String sql = "{CALL GetAssignedTeachersForBatchModule(?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, batchId);
            cs.setInt(2, moduleId);

            ResultSet rs = mySql.runQuery(conn,cs);

            // There can be ONLY ONE row
            if (rs.next()) {

                int teacherId = rs.getInt("teacherId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String departmentName = rs.getString("departmentName");

                return new model.Teacher(
                        teacherId,
                        firstName,
                        lastName,
                        departmentName
                );
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return null; // no teacher assigned yet
    }

    public boolean updateBatch(model.Batch batch, model.Module selectedModule, model.Teacher selectedTeacher) {
        Connection conn = mySql.openConnection();
        CallableStatement cs = null;
        boolean success = false;

        try {
            // Prepare the callable statement
            String sql = "{CALL UpdateBatch(?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = conn.prepareCall(sql);

            // Set procedure parameters
            cs.setInt(1, batch.getBatchId());
            cs.setString(2, batch.getBatchName());
            cs.setString(3, String.valueOf(batch.getSection()));
            cs.setInt(4, batch.getCourseYear());
            cs.setInt(5, batch.getSemester());
            cs.setString(6, batch.getRoutinePdfPath());
            cs.setInt(7, selectedModule.getModuleId());

            if (selectedTeacher != null) {
                cs.setInt(8, selectedTeacher.getTeacherId());
            } else {
                cs.setNull(8, java.sql.Types.INTEGER);
            }


            mySql.executeUpdate(conn,cs);
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            try {
                if (cs != null) cs.close();
                mySql.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return success;
    }

    public String getBatchRoutine(model.Student student) {
        String routinePath = null;
        Connection conn = mySql.openConnection();
        String sql = "SELECT routinePdfPath FROM Batch WHERE batchId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, student.getBatchId());
            ResultSet rs = mySql.runQuery(conn,ps);

            if (rs.next()) {
                routinePath = rs.getString("routinePdfPath");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching batch routine: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }

        return routinePath;
    }







}
