/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import view.AdminDashboard;
import view.AdminDashboardPanel;
import view.Enroll;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminDashboardController extends DashboardController{
    AdminDashboard adminDashboard;

    public AdminDashboardController(AdminDashboard adminDashboard) {
        this.adminDashboard=adminDashboard;
        dashboard=adminDashboard;
        buttonListener();
        enrollController();

    }

    private void enrollController()
    {
        view.Enroll panel=((AdminDashboardPanel) adminDashboard.getDashPanel()).getEnrollPanel();

        panel.addUserTypeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)panel.getUserType().getSelectedItem()).equalsIgnoreCase("admin"))
                {
                    ((CardLayout)panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(),"Admin");
                }
                else if(((String)panel.getUserType().getSelectedItem()).equalsIgnoreCase("student"))
                {
                    ((CardLayout)panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(),"Student");

                }
                else if(((String)panel.getUserType().getSelectedItem()).equalsIgnoreCase("teacher"))
                {
                    ((CardLayout)panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(),"Teacher");
                }
            }
        });
    }


}

