/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.Module;
import view.*;
import model.Teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class TeacherDashboardController extends DashboardController{
    TeacherDashboard teacherDashboard;
    Teacher teacher;


    public TeacherDashboardController(TeacherDashboard teacherDashboard, Teacher teacher) {
        this.teacherDashboard=teacherDashboard;
        dashboard=teacherDashboard;
        this.teacher=teacher;
        buttonListener();
        profilePageSetter();
        modulePageSetter();
    }

    private void profilePageSetter()
    {
        TeacherProfile profile =((TeacherProfile)teacherDashboard.getDashPanel().getProfilePanel());
        profile.setFirstName(teacher.getFirstName());
        profile.setLastName(teacher.getLastName());
        profile.setAddress(teacher.getAddress());
        profile.setContact(teacher.getContact());
        profile.setGender(teacher.getGender());
        profile.setEmail(teacher.getEmail());
        profile.setDepartment(teacher.getDepartmentName());

    }

    private void modulePageSetter(){
        dao.Module moduleDao = new Module();
        ArrayList<model.Module> teacherModules = moduleDao.getAllTeacherModules(teacher.getTeacherId());
        STModulePanel stModulePanel = ((TeacherDashboardPanel)teacherDashboard.getDashPanel()).getModulePanel();
        for(model.Module module : teacherModules)
        {
            moduleContainerPanel moduleContainer = new moduleContainerPanel();
            moduleContainer.getModuleName().setText("   "+module.getModuleName());
            moduleContainer.getModuleYear().setText("   "+String.valueOf(module.getCourseYear()));
            moduleContainer.addAddModuleActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moduleActionListener(((TeacherDashboardPanel) teacherDashboard.getDashPanel()).getModuleDisplay(),module);
                }
            });
            stModulePanel.getModuleContainer().add(moduleContainer);
        }

    }
}

