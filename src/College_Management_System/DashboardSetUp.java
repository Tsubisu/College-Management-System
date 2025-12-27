package College_Management_System;
import controller.TeacherDashboardController;
import model.Admin;
import model.Student;
import model.Teacher;
import view.AdminDashboard;
import model.User;
import controller.AdminDashboardController;
import controller.StudentDashboardController;
import controller.DashboardController;
import view.StudentDashboard;
import dao.UserData;
import view.TeacherDashboard;

public class DashboardSetUp {
    public void openDashboard(User user)
    {
        UserData userData= new UserData();
        User dashUser = userData.fetchUserData(user);
        String role=user.getRole();

        if(role.equalsIgnoreCase("Student"))
            openStudentDashboard((Student) dashUser);
        else if(role.equalsIgnoreCase("Admin"))
            openAdminDashboard((Admin) dashUser);
        else if(role.equalsIgnoreCase("Teacher"))
            openTeacherDashboard((Teacher) dashUser);
    }

    private void openStudentDashboard(Student student)
    {
        StudentDashboard studentDashboard = new StudentDashboard();
        StudentDashboardController studentDashboardController = new StudentDashboardController(studentDashboard ,student);
        studentDashboard.setVisible(true);
        System.out.print("Successful");
    }

    private void openTeacherDashboard(Teacher teacher)
    {
        TeacherDashboard teacherDashboard = new TeacherDashboard();
        TeacherDashboardController teacherDashboardController = new TeacherDashboardController(teacherDashboard,teacher);
        teacherDashboard.setVisible(true);
    }

    private void openAdminDashboard(Admin admin)
    {
        AdminDashboard adminDashboard = new AdminDashboard();
        AdminDashboardController adminDashboardController = new AdminDashboardController(adminDashboard,admin);
        adminDashboard.setVisible(true);
    }

}