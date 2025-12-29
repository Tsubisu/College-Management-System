package dao;

import database.MySqlConnection;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Course {
    private MySqlConnection mySql= MySqlConnection.getMySqlConnection();
    public ArrayList<model.Course> getAllCourse()
    {
        Connection conn = mySql.openConnection();
        String sql ="CALL GetAllCourses()";

        ArrayList<model.Course> courses= new ArrayList<>();
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            ResultSet resultSet= mySql.runQuery(conn,cs);
            while(resultSet.next()){
                int courseId= resultSet.getInt("courseId");
                String courseName= resultSet.getString("courseName");
                int departmentId=resultSet.getInt("departmentId");
                courses.add(new model.Course(courseId,courseName,departmentId));
            }
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return courses;
    }


    public boolean addCourse(model.Course course) {
        Connection conn = mySql.openConnection();
        String sql = "INSERT INTO Course (departmentId, courseName, courseDuration) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, course.getDepartmentId());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getDuration());

            mySql.executeUpdate(conn,ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    public model.Course getCourseById(int courseId) {
        Connection conn = mySql.openConnection();
        String sql = "SELECT courseId, courseName, courseDuration, departmentId FROM Course WHERE courseId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                model.Course course = new model.Course();
                course.setCourseId(rs.getInt("courseId"));
                course.setCourseName(rs.getString("courseName"));
                course.setDuration(rs.getInt("courseDuration"));
                course.setDepartmentId(rs.getInt("departmentId"));
                return course;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    public boolean updateCourse(model.Course course) {

        Connection conn = mySql.openConnection();

        String sql = "UPDATE Course SET courseName = ?, courseDuration = ? WHERE courseId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getDuration());
            ps.setInt(3, course.getCourseId());

            int rowsUpdated = mySql.executeUpdate(conn,ps);

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deleteCourse(int courseId) {

        Connection conn = mySql.openConnection();
        String sql = "DELETE FROM Course WHERE courseId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0; // true if deleted

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }




}
