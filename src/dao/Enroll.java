package dao;

import database.MySqlConnection;
import model.Admin;
import model.Batch;
import model.Course;
import model.Department;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Enroll {
    private MySqlConnection mySql = MySqlConnection.getMySqlConnection();



    public ArrayList<model.Batch> getBatchByCourse(int courseId)
    {
        Connection conn = mySql.openConnection();
        String sql ="CALL GetBatchesByCourse(?)";
        ArrayList<model.Batch> batches = new ArrayList<>();
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setInt(1,courseId);
            ResultSet resultSet= mySql.runQuery(conn,cs);
            while(resultSet.next()){
                int batchId=resultSet.getInt("batchId");
                String batchName= resultSet.getString("batch_Name");
                String section= resultSet.getString("section");
                int courseYar= resultSet.getInt("courseYear");
                int semester = resultSet.getInt("semester");
                batches.add(new Batch(batchId,batchName,section.charAt(0),courseYar,semester));

            }
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }

    return batches;
    }

    public void addStudent(String firstName, String lastName,String gender, String address, String contact, String email, int batchId)
    {
        System.out.println("Batch id"+batchId);

        Connection conn =mySql.openConnection();
        String sql="CALL AddStudent(?,?,?,?,?,?,?,?)";
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setString(1,email);
            cs.setString(2,"hardwarica123");
            cs.setString(3,gender.toUpperCase());
            cs.setString(4,firstName);
            cs.setString(5,lastName);
            cs.setString(6,address);
            cs.setString(7,contact);
            cs.setInt(8,batchId);

            mySql.runQuery(conn,cs);


        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
    }


    public void addAdmin(String firstName, String lastName,String gender, String address, String contact, String email)
    {

        Connection conn =mySql.openConnection();
        String sql="CALL AddAdmin(?,?,?,?,?,?,?)";
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setString(1,email);
            cs.setString(2,"hardwarica123");
            cs.setString(3, gender.toUpperCase());
            cs.setString(4,firstName);
            cs.setString(5, lastName);
            cs.setString(6, address);
            cs.setString(7, contact);
            mySql.runQuery(conn,cs);
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
    }


    public void addTeacher(String firstName, String lastName,String gender, String address, String contact, String email,int departmentId)
    {

        Connection conn =mySql.openConnection();
        String sql="CALL AddTeacher(?,?,?,?,?,?,?,?)";
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setString(1,email);
            cs.setString(2,"hardwarica123");
            cs.setString(3, gender.toUpperCase());
            cs.setString(4,firstName);
            cs.setString(5, lastName);
            cs.setString(6, address);
            cs.setString(7, contact);
            cs.setInt(8,departmentId);

            mySql.runQuery(conn,cs);

        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
    }





}
