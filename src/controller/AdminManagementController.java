package controller;

import dao.Enroll;
import dao.UserData;
import model.Course;
import model.Student;
import model.User;
import view.AdminDashboard;
import view.AdminDashboardPanel;
import view.Management;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminManagementController {
    private final AdminDashboard adminDashboard;
    private final Management management;
    private final dao.UserData userDao= new UserData();
    private final dao.Course course = new dao.Course();
    private final dao.Enroll enroll= new Enroll();


    public AdminManagementController(AdminDashboard adminDashboard) {
        this.adminDashboard=adminDashboard;
        management=((AdminDashboardPanel)adminDashboard.getDashPanel()).getManagement();
        studentUpdateController();
        teacherUpdateController();

    }



    private void studentUpdateController()
    {
        management.addLoadStudentDataListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String studentId= management.getStudentIdTextField().getText().trim();
                if(studentId.matches("^\\d+$"))
                {
                    Student student= validateStudentId(Integer.parseInt(studentId));
                    loadStudentData(student);
                }
                else
                {
                    JOptionPane.showMessageDialog(adminDashboard,"Enter integer value only");
                }
            }
        });
    }

    private Student validateStudentId(int studentId)
    {
        User user = new User();;
        Integer userId=userDao.getUserId("GetUserIdByStudentId",studentId);
        model.Student student = new Student();
        if(userId!=null)
        {
            user.setUserId(userId);
            user.setRole("Student");
            student=(Student) userDao.fetchUserData(user);
        }
        else
            {
                JOptionPane.showMessageDialog(adminDashboard,"No Student with such student id found");
                return null;
            }

        return student;
    }

    private void loadStudentData(Student student)
    {
        JTextField firstNameField= management.getStudentFirstNameText();
        JTextField lastNameField= management.getStudentLastNameText();
        JTextField emailField=management.getStudentEmailText();
        JTextField addressField = management.getStudentAddressText();
        JTextField contact = management.getStudentContactText();
        JComboBox<String> courseComboBox = management.getStudentCourseSelect();
        JComboBox<String> batchComboBox = management.getStudentBatchSelect();
        JComboBox<String> genderComboBox= management.getStudentGenderSelect();


        JButton updateBtn= management.getStudentUpdateBtn();


        for (ActionListener al : courseComboBox.getActionListeners()) {
            courseComboBox.removeActionListener(al);
        }

        for (ActionListener al : updateBtn.getActionListeners()) {
            updateBtn.removeActionListener(al);
        }


        ArrayList<Integer> batchId= new ArrayList<>();
        if(student!=null)
        {
            firstNameField.setText(student.getFirstName());
            lastNameField.setText(student.getLastName());
            emailField.setText(student.getEmail());
            addressField.setText(student.getAddress());
            contact.setText(student.getContact());
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Male","Female","Other"}));
            genderComboBox.setSelectedItem(student.getGender());
            ArrayList<Course> courses = course.getAllCourse();
            String[] courseNames = new String[courses.size()+1];
            courseNames[0]="Select a course";
            for (int i = 0; i < courses.size(); i++)
                courseNames[i+1] = courses.get(i).getCourseName();


            courseComboBox.setModel(new DefaultComboBoxModel<>(courseNames));


            int studentCourseId = student.getCourseId(); // from student data

            int selectedIndex = 0;
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCourseId() == studentCourseId) {
                    selectedIndex = i+1;;
                    break;

                }
            }
            if (selectedIndex != 0)
            {
                courseComboBox.setSelectedIndex(selectedIndex);
            }

            management.addStudentCourseSelectActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    batchId.clear();
                    int index = courseComboBox.getSelectedIndex();
                    if(index!=0){
                        ArrayList<model.Batch> batches = enroll.getBatchByCourse(index);
                        String[] batchNames= new String[0];
                        if(batches.isEmpty())
                        {
                            batchNames= new String[]{"No batch for this course available"};
                        }
                        else{
                            batchNames= new String[batches.size()+1];
                            batchNames[0]= "Select a batch";
                            batchId.add(-1);
                            for (int i = 0; i< batches.size(); i++)
                            {
                                batchNames[i+1] = batches.get(i).getBatchName() + " " + "\"" + batches.get(i).getSection() + "\"";
                                batchId.add(batches.get(i).getBatchId());
                            }
                        }

                        batchComboBox.setModel(new DefaultComboBoxModel<>(batchNames));
                        batchComboBox.setSelectedIndex(0);
                        if(!batchId.isEmpty())
                        {
                            for(int i =1; i<batchId.size();i++)
                            {
                                if(batchId.get(i)==student.getBatchId())
                                {
                                    batchComboBox.setSelectedIndex(i);
                                    break;
                                }


                            }
                        }
                    }
                }

            });
            courseComboBox.actionPerformed(new ActionEvent(courseComboBox,ActionEvent.ACTION_PERFORMED,"Student's Course Selected"));
        }
        else
        {
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            contact.setText("");
            courseComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select courses"}));
            batchComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select batch"}));
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select gender"}));



        }

        management.addStudentUpdatListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student updatedStudent= updateStudent(student,batchId);
                if(updatedStudent!=null)
                    if(userDao.updateStudent(updatedStudent))
                    {
                        javax.swing.JOptionPane.showMessageDialog(adminDashboard,"Student Data Updated Successfully");
                        loadStudentData(updatedStudent);
                    }


            }
        }) ;
    }

    private Student updateStudent(Student student,ArrayList<Integer> currentBatchList) {
        if (student == null || currentBatchList.isEmpty()) {
            return null;
        }

        Student updatedStudent = new Student();
        updatedStudent.setStudentId(student.getStudentId());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setGender(student.getGender());
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setAddress(student.getAddress());
        updatedStudent.setContact(student.getContact());
        updatedStudent.setBatchId(student.getBatchId());





        String newFirstName = management.getStudentFirstNameText().getText().trim();
        String newLastName = management.getStudentLastNameText().getText().trim();
        String newEmail = management.getStudentEmailText().getText().trim();
        String newAddress = management.getStudentAddressText().getText().trim();
        String newContact = management.getStudentContactText().getText().trim();
        String newGender= (String)management.getStudentGenderSelect().getSelectedItem();
        int selectedBatchId=currentBatchList.get(management.getStudentBatchSelect().getSelectedIndex());

        boolean hasAnyChange = false;
        boolean hasError = false;

        if (!newFirstName.isEmpty()) {
            if (!newFirstName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "First name cannot contain numbers");
                hasError = true;
            } else if (!newFirstName.equals(student.getFirstName())) {
                updatedStudent.setFirstName(newFirstName);
                System.out.println("First Name Changed");

                hasAnyChange = true;
            }
        }


        if (!newLastName.isEmpty()) {
            if (!newLastName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Last name cannot contain numbers");
                hasError = true;
            } else if (!newLastName.equals(student.getLastName())) {
                updatedStudent.setLastName(newLastName);
                hasAnyChange = true;
                System.out.println("Last Name Changed");
            }
        }


        if (!newEmail.isEmpty()) {
            if (!newEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Invalid email format");
                hasError = true;
            } else if (!newEmail.equals(student.getEmail())) {
                updatedStudent.setEmail(newEmail);
                hasAnyChange = true;
                System.out.println("Email Changed");
            }
        }


        if (!newAddress.isEmpty()) {
            if (!newAddress.equals(student.getAddress())) {
                updatedStudent.setAddress(newAddress);
                hasAnyChange = true;
                System.out.println("Address Changed");
            }
        }

        if (newGender!=null) {
            if (!newGender.equals(student.getGender())) {
                updatedStudent.setGender(newGender);
                hasAnyChange = true;
                System.out.println("Gender Changed to: "+ newGender);
            }
        }

        if (!newContact.isEmpty()) {
            if (!newContact.matches("^\\d{7,15}$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Contact must be numeric (7–15 digits)");
                hasError = true;
            } else if (!newContact.equals(student.getContact())) {
                updatedStudent.setContact(newContact);
                hasAnyChange = true;
                System.out.println("Contact Changed");
            }
        }
        if(selectedBatchId!=-1)
        {
            if(selectedBatchId!=student.getBatchId())
            {
                int result = JOptionPane.showConfirmDialog(
                        adminDashboard, // parent component
                        "Are you sure you want to change the batch?",
                        "Confirm Batch Change",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION)
                {
                    hasAnyChange=true;
                    updatedStudent.setBatchId(selectedBatchId);


                }
                else
                {
                    hasError=true;
                    management.getStudentBatchSelect().setSelectedIndex(0);
                }
            }
        }


        if (hasError) {
            return null;
        }

        if (!hasAnyChange) {
            JOptionPane.showMessageDialog(adminDashboard,
                    "No changes detected to update");
            return null;
        }

    return updatedStudent;
    }


    private void teacherUpdateController()
    {
        management.addLoadTeacherDataListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String teacherId= management.getTeacherIdTextField().getText().trim();
                if(teacherId.matches("^\\d+$"))
                {
                    model.Teacher teacher= validateTeacherId(Integer.parseInt(teacherId));
                    loadTeacherData(teacher);
                }
                else
                {
                    JOptionPane.showMessageDialog(adminDashboard,"Enter integer value only");
                }
            }
        });

    }
    private model.Teacher validateTeacherId(int teacherId)
    {
        User user = new User();;
        Integer userId=userDao.getUserId("GetUserIdByTeacherId",teacherId);
        model.Teacher teacher = new model.Teacher();
        if(userId!=null)
        {
            user.setUserId(userId);
            user.setRole("Teacher");
            teacher=(model.Teacher) userDao.fetchUserData(user);
        }
        else
        {
            JOptionPane.showMessageDialog(adminDashboard,"No Student with such student id found");
            return null;
        }

        return teacher;
    }

    private  void loadTeacherData(model.Teacher teacher)
    {
        JTextField firstNameField = management.getTeacherFirstNameText();
        JTextField lastNameField  = management.getTeacherLastNameText();
        JTextField emailField     = management.getTeacherEmailText();
        JTextField addressField   = management.getTeacherAddressText();
        JTextField contactField   = management.getTeacherContactText();
    }



}
