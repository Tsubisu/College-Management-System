package controller;


import dao.*;
import dao.Module;
import view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Student;

import javax.swing.*;


public class StudentDashboardController extends DashboardController {
    StudentDashboard studentDashboard;
    Student student;
    BatchDao batchDao= new BatchDao();
    dao.Module moduleDao = new Module();
    Attendance attendanceDao= new Attendance();

    public StudentDashboardController(StudentDashboard studentDashboard, Student student) {
        this.studentDashboard = studentDashboard;
        this.student = student;
        dashboard = studentDashboard;
        buttonListener();
        modulePageSetter();
        profilePageSetter();
        noticePageSetter();
        routinePageSetter();
        attendancePageSetter();
    }

    private void routinePageSetter()
    {
        String routinePath= batchDao.getBatchRoutine(student);
        JLabel routineLabel=((StudentDashboardPanel) studentDashboard.getDashPanel()).getRoutinePanel().getRoutine();
        ImageIcon icon = new ImageIcon(routinePath);
        Image img = icon.getImage().getScaledInstance(833, 497, Image.SCALE_SMOOTH);
        routineLabel.setIcon(new ImageIcon(img));

    }

    public void attendancePageSetter()
    {
        ArrayList<model.Module> studentModules = moduleDao.getAllStudentModules(student.getStudentId());
        StudentAttendance studentAttendance= ((StudentDashboardPanel )studentDashboard.getDashPanel()).getStudentAttendance();
        for(model.Module module :studentModules)
        {
            System.out.println(module.getModuleName());
            StudentAttendanceContainer studentAttendanceContainer= new StudentAttendanceContainer();
            model.Attendance attendance= attendanceDao.getStudentModuleAttendanceSummary(student.getStudentId(),module.getModuleId());
            System.out.println(attendance.getTotalPresent()+" "+attendance.getTotalAbsent()+" "+attendance.getTotalDays());
            studentAttendanceContainer.setPresentLabel(attendance.getTotalPresent());
            studentAttendanceContainer.setAbsentLabel(attendance.getTotalAbsent());
            studentAttendanceContainer.setModuleName(module.getModuleName());
            studentAttendanceContainer.setPercentage((double)(attendance.getTotalPresent()*100)/attendance.getTotalDays());
            studentAttendance.getAttendanceContentPanel().add(studentAttendanceContainer);
        }
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

