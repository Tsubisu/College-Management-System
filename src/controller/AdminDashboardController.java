/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import dao.*;
import dao.Enroll;
import dao.Module;
import model.Course;
import view.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.util.ArrayList;

import model.Admin;

import javax.swing.*;


public class AdminDashboardController extends DashboardController {
   private final AdminDashboard adminDashboard;
   private final Admin admin;
   private final LogInDAO logInDAO = new LogInDAO();
   private final Enroll enroll = new Enroll();
   private final dao.Course  course= new dao.Course();
   private final Department department= new Department();
   private final Module module= new Module();
   private final AdminManagementController adminManagementController;


    public AdminDashboardController(AdminDashboard adminDashboard, Admin admin) {
        this.adminDashboard = adminDashboard;
        dashboard = adminDashboard;
        this.admin = admin;
        profilePageSetter();
        enrollController();
        modulePageSetter();
        noticePageSetter();
        buttonListener();
        adminManagementController= new AdminManagementController(adminDashboard);


    }

    private void profilePageSetter() {
        AdminProfile profile = (AdminProfile) adminDashboard.getDashPanel().getProfilePanel();
        profile.setFirstName(admin.getFirstName());
        profile.setLastName(admin.getLastName());
        profile.setAddress(admin.getAddress());
        profile.setContact(admin.getContact());
        profile.setGender(admin.getGender());
        profile.setEmail(admin.getEmail());
    }


    private void noticePageSetter() {
        dao.Notice noticeDao= new Notice();
        ArrayList<model.Notice> notices= noticeDao.getNotices("Admin");
        AdminNoticePanel noticePanel= ((AdminDashboardPanel)adminDashboard.getDashPanel()).getNoticePanel();

        noticePanel.getNoticeContentPanel().removeAll();
        noticePanel.getNoticeContentPanel().revalidate();
        noticePanel.getNoticeContentPanel().repaint();

        for(model.Notice notice: notices)
        {
            NoticeContainer noticeContainer= new NoticeContainer();
            noticeContainer.setTitle(notice.getTitle());
            noticeContainer.setDate(notice.getPostedAt().toString());
            noticeContainer.setNoticeContent(notice.getMessage());
            noticeContainer.addReadMoreActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    javax.swing.JOptionPane.showMessageDialog(adminDashboard,notice.getMessage(),notice.getTitle().toString(),JOptionPane.INFORMATION_MESSAGE);
                }
            });

            noticePanel.getNoticeContentPanel().add(noticeContainer);
        }
        noticePanel.addAddActionButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noticePopUp();
            }
        });

    }

    private void noticePopUp()
    {
        Notice noticeDao = new Notice();
        NoticePopUp noticePopUp = new NoticePopUp();
        javax.swing.JDialog dialog= new JDialog(adminDashboard,"New Notice",true);
        dialog.setContentPane(noticePopUp);
        dialog.pack();
        dialog.setLocationRelativeTo(adminDashboard);
        noticePopUp.addAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.Notice notice= validateNoticeFields(noticePopUp);
                if(notice!=null)
                {
                    if(noticeDao.addNotice(notice))
                    {
                        javax.swing.JOptionPane.showMessageDialog(adminDashboard,"New Notice has been published");
                        noticePageSetter();
                    }
                    else
                        javax.swing.JOptionPane.showMessageDialog(adminDashboard,"Notice publishing failed");
                }
            }
        });
        dialog.setVisible(true);
    }

    private model.Notice validateNoticeFields(NoticePopUp noticePopUp)
    {
        String title= noticePopUp.getTitle().getText().trim();
        String role= (String)noticePopUp.getRole().getSelectedItem();
        String message= noticePopUp.getMessage().getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(noticePopUp, "Title cannot be empty.");
            return null;
        }

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(noticePopUp, "Message cannot be empty.");
            return null;
        }

        if (role==null) {
            JOptionPane.showMessageDialog(noticePopUp, "Role cannot be null");
            return null;
        }



        return new model.Notice(title,message,role);

    }


    private void modulePageSetter(){
        dao.Module moduleDao = new Module();

        AdminModulePanel adminModulePanel = ((AdminDashboardPanel)adminDashboard.getDashPanel()).getModulePanel();
        javax.swing.JComboBox<String> courseComboBox=adminModulePanel.getCourseComboBox();

        ArrayList<model.Course> courses= course.getAllCourse();
        String[] courseNames = new String[courses.size()];
        int[] courseId= new int[courses.size()];
        for(int i=0;i<courses.size();i++)
        {
            courseNames[i]= courses.get(i).getCourseName();
            courseId[i]= courses.get(i).getCourseId();
        }
        courseComboBox.setModel(new DefaultComboBoxModel<>(courseNames));

        adminModulePanel.addCourseComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moduleDisplay(adminModulePanel,courseId[courseComboBox.getSelectedIndex()]);
            }
        });

        adminModulePanel.addAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addModulePopUp();
            }
        });

        adminModulePanel.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteModulePopUp();

            }
        });

    }


    private void moduleDisplay(AdminModulePanel modulePanel,int courseId)
    {
        modulePanel.getModuleContainer().removeAll();
        modulePanel.getModuleContainer().revalidate();
        modulePanel.getModuleContainer().repaint();
        ArrayList<model.Module> allModules= module.getModulesByCourse(courseId);
        for(model.Module module:allModules)
        {
            moduleContainerPanel moduleContainer= new moduleContainerPanel();
            moduleContainer.getModuleName().setText("  "+module.getModuleName());
            moduleContainer.getModuleYear().setText("  "+module.getCourseYear());
            moduleContainer.addAddModuleActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moduleActionListener(((AdminDashboardPanel) adminDashboard.getDashPanel()).getModuleDisplay(),module);
                }
            });
            modulePanel.getModuleContainer().add(moduleContainer);

        }



    }

    private void deleteModulePopUp(){
        RemoveModule removeModule= new RemoveModule();
        javax.swing.JDialog dialog= new JDialog(adminDashboard,"New Module",true);
        dialog.setContentPane(removeModule);
        dialog.pack();
        dialog.setLocationRelativeTo(adminDashboard);

        removeModule.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int moduleId;
                String moduleIdText= removeModule.getModuleId().getText().trim();

                if (moduleIdText.isEmpty() || !moduleIdText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(removeModule, "Module ID must be a number.");
                }
                else
                {
                    moduleId= Integer.parseInt(moduleIdText);
                    if(moduleDao.deleteModuleById(moduleId))
                    {
                        JOptionPane.showMessageDialog(removeModule, "Module deleted successfully");
                        dialog.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(removeModule, "Module could not be deleted successfully");
                    }

                }
            }
        });
        dialog.setVisible(true);

    }

    private void addModulePopUp()
    {

        NewModule newModule= new NewModule();
        javax.swing.JDialog dialog= new JDialog(adminDashboard,"New Module",true);
        dialog.setContentPane(newModule);
        dialog.pack();
        dialog.setLocationRelativeTo(adminDashboard);


        newModule.addAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.Module module1=getModuleFromFields(newModule);
                if(module1!=null)
                {
                    if(moduleDao.addModule(module1));

                    {
                        JOptionPane.showMessageDialog(adminDashboard, "New module has been added");
                        dialog.dispose();
                    }
                }

            }
        });
        dialog.setVisible(true);
    }




    private model.Module getModuleFromFields(NewModule newModule) {
        String name =newModule.getModuleName().getText().trim();
        String durationText = newModule.getModuleDuration().getText().trim();
        String yearText = newModule.getModuleCourseYear().getText().trim();
        String semesterText = newModule.getModuleSemester().getText().trim();
        String courseIdText= newModule.getCourseId().getText().trim();

        // Validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(newModule, "Module Name cannot be empty.");
            return null;
        }
        if (durationText.isEmpty() || !durationText.matches("\\d+")) {
            JOptionPane.showMessageDialog(newModule, "Module Duration must be a number.");
            return null;
        }
        if (yearText.isEmpty() || !yearText.matches("\\d+")) {
            JOptionPane.showMessageDialog(newModule, "Course Year must be a number.");
            return null;
        }
        if (semesterText.isEmpty() || !semesterText.matches("\\d+")) {
            JOptionPane.showMessageDialog(newModule, "Course Semester must be a number.");
            return null;
        }
        if (courseIdText.isEmpty() || !courseIdText.matches("\\d+")) {
            JOptionPane.showMessageDialog(newModule, "Course ID must be a number.");
            return null;
        }

        // Parse numeric fields
        int duration = Integer.parseInt(durationText);
        int courseYear = Integer.parseInt(yearText);
        int semester = Integer.parseInt(semesterText);
        int courseId= Integer.parseInt(courseIdText);

        // Store in a Module object
        return new model.Module(0,courseId,name,courseYear,semester,duration); // return validated module
    }



    private void enrollController() {
        view.Enroll panel = ((AdminDashboardPanel) adminDashboard.getDashPanel()).getEnrollPanel();
        view.StudentEnroll studentEnroll = panel.getStudentEnroll();
        view.AdminEnroll adminEnroll = panel.getAdminEnroll();
        view.TeacherEnroll teacherEnroll=panel.getTeacherEnroll();


        setStudentEnroll(studentEnroll);
        setAdminEnroll(adminEnroll);
        setTeacherEnroll(teacherEnroll);


        panel.addUserTypeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (((String) panel.getUserType().getSelectedItem()).equalsIgnoreCase("admin")) {
                    ((CardLayout) panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(), "Admin");
                } else if (((String) panel.getUserType().getSelectedItem()).equalsIgnoreCase("student")) {
                    ((CardLayout) panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(), "Student");

                } else if (((String) panel.getUserType().getSelectedItem()).equalsIgnoreCase("teacher")) {
                    ((CardLayout) panel.getEnrollContentPanel().getLayout()).show(panel.getEnrollContentPanel(), "Teacher");
                }
            }
        });
    }

    private void setStudentEnroll(StudentEnroll studentEnroll) {
        javax.swing.JComboBox<String> courseComboBox = studentEnroll.getCourse();
        javax.swing.JComboBox<String> batchComboBox = studentEnroll.getBatch();

        ArrayList<model.Course> courses = course.getAllCourse();
        String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++)
            courseNames[i] = courses.get(i).getCourseName();


        courseComboBox.setModel(new DefaultComboBoxModel<>(courseNames));
        ArrayList<Integer> batchId = new ArrayList<>();
        studentEnroll.addCourseComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batchId.clear();

                int index = courseComboBox.getSelectedIndex();
                ArrayList<model.Batch> batches = enroll.getBatchByCourse(index+1);
                String[] batchNames = new String[batches.size()];

                for (int i = 0; i < batches.size(); i++) {
                    batchNames[i] = batches.get(i).getBatchName() + " " + "\"" + batches.get(i).getSection() + "\"";
                    batchId.add(batches.get(i).getBatchId());
                }


                batchComboBox.setModel(new DefaultComboBoxModel<>(batchNames));
            }
        });

        studentEnroll.addAddButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                enrollStudent(studentEnroll,batchId);
            }
        });




    }

    private void enrollStudent(StudentEnroll studentEnroll, ArrayList<Integer> batchId) {
        String firstName = studentEnroll.getFirstName().getText().trim();
        String lastName = studentEnroll.getLastName().getText().trim();
        String gender = (String) studentEnroll.getGender().getSelectedItem();
        String email = studentEnroll.getEmail().getText().trim();
        String contact = studentEnroll.getContact().getText().trim();
        String address = studentEnroll.getAddress().getText().trim();
        int batch = batchId.get(studentEnroll.getBatch().getSelectedIndex());
        String course = ((String) studentEnroll.getCourse().getSelectedItem()).split("\\s+")[0];


        if (!firstName.isEmpty() && !lastName.isEmpty() && gender != null && !email.isEmpty() && !contact.isEmpty() && !address.isEmpty() && batch != 0 && !course.equalsIgnoreCase("Select")) {
            if (isValidEmail(email)) {
                boolean emailFlag = logInDAO.userCheck(email);
                if (emailFlag) {
                    javax.swing.JOptionPane.showMessageDialog(studentEnroll, "User with such email already exists");
                } else {
                    System.out.println("Adding student");
                    javax.swing.JOptionPane.showMessageDialog(studentEnroll, "New Student "+firstName+ " added");
                    enroll.addStudent(firstName, lastName, gender, address, contact, email, batch);
                }
            } else {
                JOptionPane.showMessageDialog(studentEnroll, "Enter a valid email address");
            }
        }
    }

    private void setAdminEnroll(AdminEnroll adminEnroll)
    {
        adminEnroll.addAddButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollAdmin(adminEnroll);
            }
        });
    }

    private void enrollAdmin(AdminEnroll adminEnroll)
    {
        String firstName = adminEnroll.getFirstName().getText().trim();
        String lastName = adminEnroll.getLastName().getText().trim();
        String gender = (String) adminEnroll.getGender().getSelectedItem();
        String email = adminEnroll.getEmail().getText().trim();
        String contact = adminEnroll.getContact().getText().trim();
        String address = adminEnroll.getAddress().getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty() && gender != null && !email.isEmpty() && !contact.isEmpty() && !address.isEmpty() ) {
            if (isValidEmail(email)) {
                boolean emailFlag = logInDAO.userCheck(email);
                if (emailFlag) {
                    javax.swing.JOptionPane.showMessageDialog(adminEnroll, "User with such email already exists");
                } else {
                    System.out.println("Adding student");
                    javax.swing.JOptionPane.showMessageDialog(adminEnroll, "New Admin "+firstName+ " added");
                    enroll.addAdmin(firstName, lastName, gender, address, contact, email);
                }
            } else {
                JOptionPane.showMessageDialog(adminEnroll, "Enter a valid email address");
            }
        }
    }


    private void setTeacherEnroll(TeacherEnroll teacherEnroll)
    {
        javax.swing.JComboBox<String> departmentComboBox = teacherEnroll.getDepartment();
        ArrayList<model.Department> departments = department.getDepartment();
        String[] departmentNames= new String[departments.size()];
        int[] departmentId= new int[departments.size()];
        for(int i =0;i<departments.size();i++)
        {
            departmentNames[i]= departments.get(i).getDepartmentName();
            departmentId[i]=departments.get(i).getDepartmentId();
        }

        departmentComboBox.setModel(new DefaultComboBoxModel<>(departmentNames));

        teacherEnroll.addAddButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollTeacher(teacherEnroll,departmentId);

            }
        });

      ;
    }


    private void enrollTeacher(TeacherEnroll teacherEnroll,int[] departmentId)
    {
        String firstName = teacherEnroll.getFirstName().getText().trim();
        String lastName = teacherEnroll.getLastName().getText().trim();
        String gender = (String) teacherEnroll.getGender().getSelectedItem();
        String email = teacherEnroll.getEmail().getText().trim();
        String contact = teacherEnroll.getContact().getText().trim();
        String address = teacherEnroll.getAddress().getText().trim();
        int id= departmentId[teacherEnroll.getDepartment().getSelectedIndex()];

        if (!firstName.isEmpty() && !lastName.isEmpty() && gender != null && !email.isEmpty() && !contact.isEmpty() && !address.isEmpty() && id!=0)
        {
            if (isValidEmail(email))
            {
                boolean emailFlag = logInDAO.userCheck(email);
                if (emailFlag) {
                    JOptionPane.showMessageDialog(teacherEnroll, "User with such email already exists");
                } else {
                    System.out.println("Adding Teacher");
                    javax.swing.JOptionPane.showMessageDialog(teacherEnroll, "New Teacher "+firstName+ " added");
                    enroll.addTeacher(firstName, lastName, gender, address, contact, email,id);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(teacherEnroll, "Enter a valid email address");
            }
        }
    }
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

}

