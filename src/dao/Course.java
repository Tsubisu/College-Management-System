package dao;

import database.MySqlConnection;

import java.sql.CallableStatement;
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
}
