/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;



import view.TeacherDashboard;


public class TeacherDashboardController extends DashboardController{
    TeacherDashboard teacherDashboard;

    public TeacherDashboardController(TeacherDashboard teacherDashboard) {
        this.teacherDashboard=teacherDashboard;
        dashboard=teacherDashboard;
        buttonListener();

}
}

