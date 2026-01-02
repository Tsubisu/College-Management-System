/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import com.toedter.calendar.JDateChooser;
import dao.Attendance;
import dao.BatchDao;
import dao.Module;
import dao.Notice;
import view.*;
import model.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;


public class TeacherDashboardController extends DashboardController{
    TeacherDashboard teacherDashboard;
    Teacher teacher;
    BatchDao batchDao= new BatchDao();
    Attendance attendanceDao= new Attendance();


    public TeacherDashboardController(TeacherDashboard teacherDashboard, Teacher teacher) {
        this.teacherDashboard=teacherDashboard;
        dashboard=teacherDashboard;
        this.teacher=teacher;
        buttonListener();
        profilePageSetter();
        modulePageSetter();
        noticePageSetter();
        attendancePageSetter();
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

    private void routinePageSetter()
    {
        String routinePath= teacher.getRoutinePdfPath();
        JLabel routineLabel=((TeacherDashboardPanel) teacherDashboard.getDashPanel()).getRoutinePanel().getRoutine();
        ImageIcon icon = new ImageIcon(routinePath);
        Image img = icon.getImage().getScaledInstance(833, 497, Image.SCALE_SMOOTH);
        routineLabel.setIcon(new ImageIcon(img));

    }


    private void attendancePageSetter()
    {
        ArrayList<model.Batch> teachableBatches=  batchDao.getBatchesByTeacher(teacher.getTeacherId());
        TeacherAttendance teacherAttendance=( (TeacherDashboardPanel) teacherDashboard.getDashPanel()).getTeacherAttendance();
        JComboBox<model.Batch> batchComboBox=teacherAttendance.getBatchComboBox();
        JDateChooser dateChooser= teacherAttendance.getDateChooser();
        JTable attendanceTable= teacherAttendance.getAttendanceTable();
        DefaultTableModel tableModel= (DefaultTableModel) attendanceTable.getModel();
        tableModel.setRowCount(0);

        for(model.Batch batch:teachableBatches)
        {
            System.out.println(batch.getBatchName());
        }




        batchComboBox.setModel(new DefaultComboBoxModel<>(teachableBatches.toArray(new model.Batch[0])));

        teacherAttendance.addTeachableBatchActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                attendanceTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                attendanceTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

                if(!teachableBatches.isEmpty())
                {
                    int batchId= ((model.Batch) batchComboBox.getSelectedItem()).getBatchId();
                    ArrayList<model.Student> batchStudents=batchDao.getStudentsByBatch(batchId);
                    for (int i = 0; i < batchStudents.size(); i++)

                        tableModel.addRow(new Object[]{batchStudents.get(i).getStudentId(),
                                batchStudents.get(i).getFirstName()+" "+ batchStudents.get(i).getLastName(),
                                Boolean.FALSE}
                        );
                }
            }
        });

        teacherAttendance.addSubmitActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAttendance();
            }
        });

    }

    private void submitAttendance() {
        TeacherAttendance teacherAttendance = ((TeacherDashboardPanel) teacherDashboard.getDashPanel()).getTeacherAttendance();
        JTable attendanceTable = teacherAttendance.getAttendanceTable();
        JComboBox<model.Batch> batchComboBox = teacherAttendance.getBatchComboBox();
        JDateChooser dateChooser = teacherAttendance.getDateChooser();
        model.Batch selectedBatch = (model.Batch) batchComboBox.getSelectedItem();

        if (selectedBatch == null) {
            JOptionPane.showMessageDialog(null, "Please select a batch!");
            return;
        }

        java.util.Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a date!");
            return;
        }

        int batchId = selectedBatch.getBatchId();
        int moduleId = selectedBatch.getModuleId();

        if (moduleId == 0) {
            JOptionPane.showMessageDialog(null, "No module selected for this batch!");
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) attendanceTable.getModel();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int studentId = (int) tableModel.getValueAt(i, 0); // studentId
            Boolean isPresent = (Boolean) tableModel.getValueAt(i, 2); // checkbox column
            String status = isPresent != null && isPresent ? "Present" : "Absent";

            attendanceDao.markAttendance(studentId, moduleId, batchId, selectedDate, status);
        }

        JOptionPane.showMessageDialog(null, "Attendance saved successfully!");
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


    private void noticePageSetter() {
        dao.Notice noticeDao= new Notice();
        ArrayList<model.Notice> notices= noticeDao.getNotices("Teacher");
        NoticePanel noticePanel= ((TeacherDashboardPanel)teacherDashboard.getDashPanel()).getNoticePanel();
        for(model.Notice notice: notices)
        {
            NoticeContainer noticeContainer= new NoticeContainer();
            noticeContainer.setTitle(notice.getTitle());
            noticeContainer.setDate(notice.getPostedAt().toString());
            noticeContainer.setNoticeContent(notice.getMessage());
            noticeContainer.addReadMoreActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    javax.swing.JOptionPane.showMessageDialog(teacherDashboard,notice.getMessage(),notice.getTitle().toString(), JOptionPane.INFORMATION_MESSAGE);
                }
            });

            noticePanel.getNoticeContentPanel().add(noticeContainer);


        }

    }
}

