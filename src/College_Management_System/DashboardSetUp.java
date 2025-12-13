package College_Management_System;
import view.AdminDashboard;
import model.User;
import controller.AdminDashboardController;
import controller.StudentDashboardController;
import controller.DashboardController;
public class DashboardSetUp {
    private  DashboardController dashboardController;


    public void openDashboard(User user)
    {
        System.out.print("opened a dashboard");
        String role = user.getRole();
        if(role.equalsIgnoreCase("Student"))
        {
            dashboardController = new StudentDashboardController(new view.StudentDashboard());

        }
        else if(role.equalsIgnoreCase("Admin"))
        {
            dashboardController= new AdminDashboardController(new AdminDashboard());
        }
        else
            {
                System.out.println("not currently available");
            }

    }

}