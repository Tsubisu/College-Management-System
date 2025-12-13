package College_Management_System;
import view.*;
import controller.*;

public class demo
{
    public static void main(String[] args)
    {
        AdminDashboard adminDashboard = new AdminDashboard();
        DashboardController dashboardController = new AdminDashboardController(adminDashboard);
        adminDashboard.setVisible(true);
    }
}