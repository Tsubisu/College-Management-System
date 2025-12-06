package controller;
import view.AdminDashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController  {
    private final AdminDashboard adminDashboard;
    public AdminDashboardController(AdminDashboard adminDashboard)
    {
        this.adminDashboard=adminDashboard;
        adminDashboard.addProflileButtonListener(new ActionListener()
           {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                   System.out.println("profile button pressed");
               }
           });

        adminDashboard.addAttendanceButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("attendance button pressed");
            }
        });

        adminDashboard.addModuleButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Module button pressed");
            }
        });

        adminDashboard.addRoutineButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Routine button pressed");
            }
        });

        adminDashboard.addNoticeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Notice button pressed");
            }
        });

        adminDashboard.addLogOutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LogOut button pressed");
            }
        });

        adminDashboard.addEnrollButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Enroll button pressed");
            }
        });

        adminDashboard.addManagementButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Management button pressed");
            }
    });
}

}