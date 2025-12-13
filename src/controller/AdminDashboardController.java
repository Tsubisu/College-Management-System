/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import view.AdminDashboard;


public class AdminDashboardController extends DashboardController{
    AdminDashboard adminDashboard;

    public AdminDashboardController(AdminDashboard adminDashboard) {
        this.adminDashboard=adminDashboard;
        dashboard=adminDashboard;
        buttonListener();
    }


}

