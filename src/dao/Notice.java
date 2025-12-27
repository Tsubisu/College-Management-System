package dao;

import database.MySqlConnection;
import model.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;

public class Notice {
    MySqlConnection mySql= MySqlConnection.getMySqlConnection();

    public ArrayList<model.Notice> getNotices(String role)
    {
        Connection conn = mySql.openConnection();
        String sql="CALL getNotificationsByRole(?)";

        ArrayList<model.Notice> notices = new ArrayList<>();
        try(CallableStatement cs = conn.prepareCall(sql))
        {
            cs.setString(1,role);
            ResultSet resultSet= mySql.runQuery(conn,cs);
            while(resultSet.next()){
                int notificationId= resultSet.getInt("notificationId");
                String title = resultSet.getString("title");
                String message = resultSet.getString("message");
                java.sql.Date postedAt= resultSet.getDate("postedAt");

                model.Notice notice= new model.Notice();
                notice.setNotificationId(notificationId);
                notice.setTitle(title);
                notice.setMessage(message);
                if (postedAt != null) {
                    notice.setPostedAt(postedAt.toLocalDate());
                }

                notices.add(notice);
            }
        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }

        return notices;
    }
}
