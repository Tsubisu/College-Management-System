package controller;


import view.StudentDashboard;



public class StudentDashboardController extends DashboardController{
    StudentDashboard studentDashboard;

    public StudentDashboardController(StudentDashboard studentDashboard) {
        this.studentDashboard=studentDashboard;
        dashboard=studentDashboard;
        buttonListener();

    }


}

