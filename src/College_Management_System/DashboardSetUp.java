package College_Management_System;
import view.AdminDashboard;
import model.User;
import controller.AdminDashboardController;
import controller.StudentDashboardController;
import controller.DashboardController;
import view.StudentDashboard;

public class DashboardSetUp {
    private  DashboardController dashboardController;


    public void openDashboard(User user)
    {
        System.out.print("opened a dashboard");
        String role = user.getRole();
        if(role.equalsIgnoreCase("Student"))
        {
            StudentDashboard studentDashboard = new StudentDashboard();
            dashboardController = new StudentDashboardController(studentDashboard);
            dashboardController.profilePageSetter(user);
            studentDashboard.setVisible(true);


        }
        else if(role.equalsIgnoreCase("Admin"))
        {
            view.AdminDashboard adminDashboard = new AdminDashboard();
            dashboardController= new AdminDashboardController(adminDashboard);
            dashboardController.profilePageSetter(user);
            adminDashboard.setVisible(true);
        }
        else
            {
                System.out.println("not currently available");
            }

    }

}