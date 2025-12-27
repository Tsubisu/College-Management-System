package dao;

import database.MySqlConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Department {
    private MySqlConnection mySql = database.MySqlConnection.getMySqlConnection();
    public ArrayList<model.Department> getDepartment()
    {
        Connection conn = mySql.openConnection();
        String sql = "CALL GetAllDepartments()";
        ArrayList<model.Department> department= new ArrayList<>();
        try(CallableStatement cs =conn.prepareCall(sql))
        {
            ResultSet resultSet= mySql.runQuery(conn,cs);
            while(resultSet.next())
            {
                int departmentId= resultSet.getInt("departmentId");
                String departmentName = resultSet.getString("departmentName");
                department.add(new model.Department(departmentId,departmentName));
            }

        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }

        return department;
    }

}
