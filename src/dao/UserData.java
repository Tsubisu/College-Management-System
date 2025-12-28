package dao;

import database.MySqlConnection;
import model.Admin;
import model.Student;
import model.Teacher;
import model.User;
//import model.Teacher;
//import model.Admin;

import java.sql.*;


public class UserData {

    private MySqlConnection mySql= MySqlConnection.getMySqlConnection();

    public User fetchUserData(User user)
    {
        Connection conn = mySql.openConnection();
        String sql = "CALL GetUserDetails(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1,user.getUserId());
            ResultSet result=mySql.runQuery(conn, cs);
            result.next();

            String firstName,
                    lastName,
                    email,
                    password,
                    address,
                    gender,
                    contact,
                    role,
                    courseName,
                    batchName,
                    departmentName;
            int courseId,
                year,
                semester,
                batchId,
                studentId,
                teacherId,
                adminId,
                departmentId;

            firstName=result.getString("firstName");
            lastName=result.getString("lastName");
            email=result.getString("email");
            password=result.getString("userPassword");
            address=result.getString("address");
            contact=result.getString("contact");
            gender=result.getString("gender");
            if (user.getRole().equalsIgnoreCase("Student"))
            {
                studentId= result.getInt("studentId");
                System.out.print("StudentId is "+studentId);
                courseId= result.getInt("courseId");
                courseName=result.getString("courseName");
                year=result.getInt("courseYear");
                semester=result.getInt("semester");
                batchId=result.getInt("batchId");
                batchName=result.getString("batch_name");

                return new Student(user.getUserId(),studentId, firstName,lastName,email,password,address,contact,gender,
                                                    batchId,batchName,courseId,courseName,year,semester);

            }
            else if (user.getRole().equalsIgnoreCase("Teacher"))
            {
                teacherId=result.getInt("teacherId");
                departmentId=result.getInt("departmentId");
                departmentName=result.getString("departmentName");

                return new Teacher(user.getUserId(),teacherId,firstName,lastName,email,password,address,contact,gender,departmentId,departmentName);
            }
            else
            {
                return new Admin(user.getUserId(),firstName,lastName,email,password,address,contact,gender);
            }

        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }

//        return user;
        throw new IllegalStateException("Unknown role:"+user.getRole());
    }


    public Integer getUserId(String procedureName, int id) {
        Connection conn= mySql.openConnection();
        String sql = "{ CALL " + procedureName + "(?) }";

        try (CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setInt(1, id);
            ResultSet rs = mySql.runQuery(conn,cs);
            if (rs.next()) {
                return rs.getInt("userId");
            }
        }
        catch (SQLException e)
        {
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return null;
    }


    public boolean updateStudent(Student student)
    { Connection conn= mySql.openConnection();
        String sql = "{CALL UpdateStudent(?,?,?,?,?,?,?,?)}";

        try (CallableStatement cs = conn.prepareCall(sql)){

            cs.setInt(1, student.getStudentId());
            cs.setString(2, student.getEmail());
            cs.setString(3, student.getGender());
            cs.setString(4, student.getFirstName());
            cs.setString(5, student.getLastName());
            cs.setString(6, student.getAddress());
            cs.setString(7, student.getContact());
            cs.setInt(8, student.getBatchId());

            mySql.executeUpdate(conn,cs);
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Failed to update student:\n" + e.getMessage()
            );
            return false;

        } finally {
            mySql.closeConnection(conn);
        }

    }

    public boolean updateTeacher(Teacher teacher)
    { Connection conn= mySql.openConnection();
        String sql = "{CALL UpdateTeacher(?,?,?,?,?,?,?,?)}";

        try (CallableStatement cs = conn.prepareCall(sql)){

                cs.setInt(1, teacher.getTeacherId());
                cs.setString(2, teacher.getFirstName());
                cs.setString(3, teacher.getLastName());
                cs.setString(4, teacher.getEmail());
                cs.setString(5, teacher.getAddress());
                cs.setString(6, teacher.getContact());
                cs.setString(7, teacher.getGender());
                cs.setInt(8, teacher.getDepartmentId());

            mySql.executeUpdate(conn,cs);
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Failed to update teacher:\n" + e.getMessage()
            );
            return false;

        } finally {
            mySql.closeConnection(conn);
        }

    }
}
