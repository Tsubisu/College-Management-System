package controller;

import view.StudentDashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboardController  {
    private StudentDashboard studentDashboard;
    public StudentDashboardController(StudentDashboard studentDashboard)
    {
        this.studentDashboard=studentDashboard;
        studentDashboard.addProflileButtonListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("profile button pressed");
            }
        }
        );

        studentDashboard.addAttendanceButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("attendance button pressed");
            }
        });

        studentDashboard.addModuleButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Module button pressed");
            }
        });

        studentDashboard.addRoutineButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Routine button pressed");
            }
        });

        studentDashboard.addNoticeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Notice button pressed");
            }
        });

        studentDashboard.addLogOutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LogOut button pressed");
            }
        });
    }

}
