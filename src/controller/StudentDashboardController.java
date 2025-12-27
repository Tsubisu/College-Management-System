package controller;


import dao.Module;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Student;


public class StudentDashboardController extends DashboardController{
    StudentDashboard studentDashboard;
    Student student;

    public StudentDashboardController(StudentDashboard studentDashboard, Student student) {
        this.studentDashboard=studentDashboard;
        this.student=student;
        dashboard=studentDashboard;
        buttonListener();
        modulePageSetter();
        profilePageSetter();
    }


    private void modulePageSetter(){
        dao.Module moduleDao = new Module();
        ArrayList<model.Module> studentModules = moduleDao.getAllStudentModules(student.getStudentId());
        STModulePanel stModulePanel = ((StudentDashboardPanel)studentDashboard.getDashPanel()).getModulePanel();
        for(model.Module module : studentModules)
        {
            moduleContainerPanel moduleContainer = new moduleContainerPanel();
            moduleContainer.getModuleName().setText("   "+module.getModuleName());
            moduleContainer.getModuleYear().setText("   "+String.valueOf(module.getCourseYear()));
            moduleContainer.addAddModuleActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moduleActionListener(((StudentDashboardPanel) studentDashboard.getDashPanel()).getModuleDisplay(),module);
                }
            });
            stModulePanel.getModuleContainer().add(moduleContainer);
        }

    }

    private void profilePageSetter()
    {
        StudentProfile profile =((StudentProfile)studentDashboard.getDashPanel().getProfilePanel());
        profile.setFirstName(student.getFirstName());
        profile.setLastName(student.getLastName());
        profile.setAddress(student.getAddress());
        profile.setContact(student.getContact());
        profile.setGender(student.getGender());
        profile.setEmail(student.getEmail());
        profile.setCourse(student.getCourseName());
        profile.setBatch(student.getBatch());
        profile.setYear(String.valueOf(student.getYear()));
    }




}

