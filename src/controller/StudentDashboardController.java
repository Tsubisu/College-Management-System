package controller;


import dao.BatchDao;
import dao.Module;
import dao.Notice;
import java.awt.Image;
import view.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Student;

import javax.swing.*;


public class StudentDashboardController extends DashboardController {
    StudentDashboard studentDashboard;
    Student student;
    BatchDao batchDao = new BatchDao();
    public StudentDashboardController(StudentDashboard studentDashboard, Student student) {
        this.studentDashboard = studentDashboard;
        this.student = student;
        dashboard = studentDashboard;
        buttonListener();
        modulePageSetter();
        profilePageSetter();
        noticePageSetter();
        routinePageSetter();
    }


    private void noticePageSetter() {
        dao.Notice noticeDao= new Notice();
        ArrayList<model.Notice> notices= noticeDao.getNotices("Student");
        NoticePanel noticePanel= ((StudentDashboardPanel)studentDashboard.getDashPanel()).getNoticePanel();
        for(model.Notice notice: notices)
        {
            NoticeContainer noticeContainer= new NoticeContainer();
            noticeContainer.setTitle(notice.getTitle());
            noticeContainer.setDate(notice.getPostedAt().toString());
            noticeContainer.setNoticeContent(notice.getMessage());
            noticeContainer.addReadMoreActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    javax.swing.JOptionPane.showMessageDialog(studentDashboard,notice.getMessage(),notice.getTitle().toString(),JOptionPane.INFORMATION_MESSAGE);
                }
            });

            noticePanel.getNoticeContentPanel().add(noticeContainer);


        }

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


    private void routinePageSetter()
        {
            String routinePath= batchDao.getBatchRoutine(student);
            JLabel routineLabel=((StudentDashboardPanel) studentDashboard.getDashPanel()).getRoutinePanel().getRoutine();
            ImageIcon icon = new ImageIcon(routinePath);
            Image img = icon.getImage().getScaledInstance(833, 497, Image.SCALE_SMOOTH);
            routineLabel.setIcon(new ImageIcon(img));

        }

}

