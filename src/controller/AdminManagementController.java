package controller;

import dao.BatchDao;
import dao.Department;
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
    private final dao.UserData userDao = new UserData();
    private final dao.Course courseDao = new dao.Course();
    private final dao.Enroll enroll = new Enroll();
    private final dao.Department department = new Department();
    private final dao.BatchDao batchDao= new BatchDao();



    public AdminManagementController(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
        management = ((AdminDashboardPanel) adminDashboard.getDashPanel()).getManagement();
        studentUpdateController();
        teacherUpdateController();
        adminUpdateController();
        courseManagementController();
        batchController();
    }


    private void studentUpdateController() {
        management.addLoadStudentDataListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String studentId = management.getStudentIdTextField().getText().trim();
                if (studentId.matches("^\\d+$")) {
                    Student student = validateStudentId(Integer.parseInt(studentId));
                    loadStudentData(student);
                } else {
                    JOptionPane.showMessageDialog(adminDashboard, "Enter integer value only");
                }
            }
        });
    }

    private Student validateStudentId(int studentId) {
        User user = new User();
        ;
        Integer userId = userDao.getUserId("GetUserIdByStudentId", studentId);
        model.Student student = new Student();
        if (userId != null) {
            user.setUserId(userId);
            user.setRole("Student");
            student = (Student) userDao.fetchUserData(user);
        } else {
            JOptionPane.showMessageDialog(adminDashboard, "No Student with such student id found");
            return null;
        }

        return student;
    }

    private void loadStudentData(Student student) {
        JTextField firstNameField = management.getStudentFirstNameText();
        JTextField lastNameField = management.getStudentLastNameText();
        JTextField emailField = management.getStudentEmailText();
        JTextField addressField = management.getStudentAddressText();
        JTextField contact = management.getStudentContactText();
        JComboBox<String> courseComboBox = management.getStudentCourseSelect();
        JComboBox<String> batchComboBox = management.getStudentBatchSelect();
        JComboBox<String> genderComboBox = management.getStudentGenderSelect();


        JButton updateBtn = management.getStudentUpdateBtn();


        for (ActionListener al : courseComboBox.getActionListeners()) {
            courseComboBox.removeActionListener(al);
        }

        for (ActionListener al : updateBtn.getActionListeners()) {
            updateBtn.removeActionListener(al);
        }


        ArrayList<Integer> batchId = new ArrayList<>();
        if (student != null) {
            firstNameField.setText(student.getFirstName());
            lastNameField.setText(student.getLastName());
            emailField.setText(student.getEmail());
            addressField.setText(student.getAddress());
            contact.setText(student.getContact());
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Male", "Female", "Other"}));
            genderComboBox.setSelectedItem(student.getGender());
            ArrayList<Course> courses = courseDao.getAllCourse();
            String[] courseNames = new String[courses.size() + 1];
            courseNames[0] = "Select a course";
            for (int i = 0; i < courses.size(); i++)
                courseNames[i + 1] = courses.get(i).getCourseName();


            courseComboBox.setModel(new DefaultComboBoxModel<>(courseNames));


            int studentCourseId = student.getCourseId(); // from student data

            int selectedIndex = 0;
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCourseId() == studentCourseId) {
                    selectedIndex = i + 1;
                    ;
                    break;

                }
            }
            if (selectedIndex != 0) {
                courseComboBox.setSelectedIndex(selectedIndex);
            }

            management.addStudentCourseSelectActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    batchId.clear();
                    int index = courseComboBox.getSelectedIndex();
                    if (index != 0) {
                        ArrayList<model.Batch> batches = enroll.getBatchByCourse(index);
                        String[] batchNames = new String[0];
                        if (batches.isEmpty()) {
                            batchNames = new String[]{"No batch for this course available"};
                        } else {
                            batchNames = new String[batches.size() + 1];
                            batchNames[0] = "Select a batch";
                            batchId.add(-1);
                            for (int i = 0; i < batches.size(); i++) {
                                batchNames[i + 1] = batches.get(i).getBatchName() + " " + "\"" + batches.get(i).getSection() + "\"";
                                batchId.add(batches.get(i).getBatchId());
                            }
                        }

                        batchComboBox.setModel(new DefaultComboBoxModel<>(batchNames));
                        batchComboBox.setSelectedIndex(0);
                        if (!batchId.isEmpty()) {
                            for (int i = 1; i < batchId.size(); i++) {
                                if (batchId.get(i) == student.getBatchId()) {
                                    batchComboBox.setSelectedIndex(i);
                                    break;
                                }


                            }
                        }
                    }
                }

            });
            courseComboBox.actionPerformed(new ActionEvent(courseComboBox, ActionEvent.ACTION_PERFORMED, "Student's Course Selected"));
        } else {
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            contact.setText("");
            courseComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select courses"}));
            batchComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select batch"}));
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter student id to select gender"}));


        }

        management.addStudentUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student updatedStudent = updateStudent(student, batchId);
                if (updatedStudent != null)
                    if (userDao.updateStudent(updatedStudent)) {
                        javax.swing.JOptionPane.showMessageDialog(adminDashboard, "Student Data Updated Successfully");
                        loadStudentData(updatedStudent);
                    }


            }
        });
    }

    private Student updateStudent(Student student, ArrayList<Integer> currentBatchList) {
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
        String newGender = (String) management.getStudentGenderSelect().getSelectedItem();
        int selectedBatchId = currentBatchList.get(management.getStudentBatchSelect().getSelectedIndex());

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

        if (newGender != null) {
            if (!newGender.equals(student.getGender())) {
                updatedStudent.setGender(newGender);
                hasAnyChange = true;
                System.out.println("Gender Changed to: " + newGender);
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
        if (selectedBatchId != -1) {
            if (selectedBatchId != student.getBatchId()) {
                int result = JOptionPane.showConfirmDialog(
                        adminDashboard, // parent component
                        "Are you sure you want to change the batch?",
                        "Confirm Batch Change",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    hasAnyChange = true;
                    updatedStudent.setBatchId(selectedBatchId);


                } else {
                    hasError = true;
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


    private void teacherUpdateController() {
        management.addLoadTeacherDataListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String teacherId = management.getTeacherIdTextField().getText().trim();
                if (teacherId.matches("^\\d+$")) {
                    model.Teacher teacher = validateTeacherId(Integer.parseInt(teacherId));
                    loadTeacherData(teacher);
                } else {
                    JOptionPane.showMessageDialog(adminDashboard, "Enter integer value only");
                }
            }
        });

    }

    private model.Teacher validateTeacherId(int teacherId) {
        User user = new User();
        ;
        Integer userId = userDao.getUserId("GetUserIdByTeacherId", teacherId);
        model.Teacher teacher = new model.Teacher();
        if (userId != null) {
            user.setUserId(userId);
            user.setRole("Teacher");
            teacher = (model.Teacher) userDao.fetchUserData(user);
        } else {
            JOptionPane.showMessageDialog(adminDashboard, "No Teacher with such id found");
            return null;
        }

        return teacher;
    }

    private void loadTeacherData(model.Teacher teacher) {
        JTextField firstNameField = management.getTeacherFirstNameText();
        JTextField lastNameField = management.getTeacherLastNameText();
        JTextField emailField = management.getTeacherEmailText();
        JTextField addressField = management.getTeacherAddressText();
        JTextField contactField = management.getTeacherContactText();
        JComboBox<String> gender = management.getTeacherGender();
        gender.setModel(new DefaultComboBoxModel<>(new String[]{"Enter TeacherId to select gender"}));
        JComboBox<String> departmentComboBox = management.getTeacherDepartment();
        JButton updateBtn = management.getTeacherUpdateBtn();


        for (ActionListener al : updateBtn.getActionListeners()) {
            updateBtn.removeActionListener(al);
        }

        ArrayList<Integer> departmentId = new ArrayList<>();

        if (teacher != null) {
            firstNameField.setText(teacher.getFirstName());
            lastNameField.setText(teacher.getLastName());
            emailField.setText(teacher.getEmail());
            addressField.setText(teacher.getAddress());
            contactField.setText(teacher.getContact());
            gender.setModel(new DefaultComboBoxModel<>(new String[]{"Male", "Female", "Other"}));
            gender.setSelectedItem(teacher.getGender());

            ArrayList<model.Department> departments = department.getDepartment();
            String[] departmentNames = new String[departments.size() + 1];

            departmentNames[0] = "Select a department";
            departmentId.add(-1);

            for (int i = 0; i < departments.size(); i++) {
                departmentNames[i + 1] = departments.get(i).getDepartmentName();
                departmentId.add(departments.get(i).getDepartmentId());
            }
            departmentComboBox.setModel(new DefaultComboBoxModel<>(departmentNames));

            departmentComboBox.setSelectedIndex(0);
            if (!departmentId.isEmpty())
                for (int i = 1; i < departmentId.size(); i++)
                    if (departmentId.get(i) == teacher.getDepartmentId()) {
                        departmentComboBox.setSelectedIndex(i);
                        break;
                    }
        } else {
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            contactField.setText("");
            gender.setModel(new DefaultComboBoxModel<>(new String[]{"Enter TeacherId to select Gender"}));
            departmentComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter TeacherId to select Departments"}));

        }
        management.addTeacherUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.Teacher updatedTeacher = updateTeacher(teacher, departmentId);
                if (updatedTeacher != null)
                    if (userDao.updateTeacher(updatedTeacher)) {
                        javax.swing.JOptionPane.showMessageDialog(adminDashboard, "Teacher Data Updated Successfully");
                        loadTeacherData(updatedTeacher);
                    }

            }
        });

    }

    public model.Teacher updateTeacher(model.Teacher teacher, ArrayList<Integer> departmentList) {
        if (teacher == null || departmentList.isEmpty()) {
            return null;
        }

        model.Teacher updatedTeacher = new model.Teacher();
        updatedTeacher.setTeacherId(teacher.getTeacherId());
        updatedTeacher.setEmail(teacher.getEmail());
        updatedTeacher.setGender(teacher.getGender());
        updatedTeacher.setFirstName(teacher.getFirstName());
        updatedTeacher.setLastName(teacher.getLastName());
        updatedTeacher.setAddress(teacher.getAddress());
        updatedTeacher.setContact(teacher.getContact());
        updatedTeacher.setDepartmentId(teacher.getDepartmentId());

        String newFirstName = management.getTeacherFirstNameText().getText().trim();
        String newLastName = management.getTeacherLastNameText().getText().trim();
        String newEmail = management.getTeacherEmailText().getText().trim();
        String newAddress = management.getTeacherAddressText().getText().trim();
        String newContact = management.getTeacherContactText().getText().trim();
        String newGender = (String) management.getTeacherGender().getSelectedItem();
        int selectedDepartmentId = departmentList.get(management.getTeacherDepartment().getSelectedIndex());

        boolean hasAnyChange = false;
        boolean hasError = false;

// First Name
        if (!newFirstName.isEmpty()) {
            if (!newFirstName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "First name cannot contain numbers");
                hasError = true;
            } else if (!newFirstName.equals(teacher.getFirstName())) {
                updatedTeacher.setFirstName(newFirstName);
                hasAnyChange = true;
                System.out.println("First Name Changed");
            }
        }

// Last Name
        if (!newLastName.isEmpty()) {
            if (!newLastName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Last name cannot contain numbers");
                hasError = true;
            } else if (!newLastName.equals(teacher.getLastName())) {
                updatedTeacher.setLastName(newLastName);
                hasAnyChange = true;
                System.out.println("Last Name Changed");
            }
        }

// Email
        if (!newEmail.isEmpty()) {
            if (!newEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Invalid email format");
                hasError = true;
            } else if (!newEmail.equals(teacher.getEmail())) {
                updatedTeacher.setEmail(newEmail);
                hasAnyChange = true;
                System.out.println("Email Changed");
            }
        }

// Address
        if (!newAddress.isEmpty()) {
            if (!newAddress.equals(teacher.getAddress())) {
                updatedTeacher.setAddress(newAddress);
                hasAnyChange = true;
                System.out.println("Address Changed");
            }
        }

// Gender
        if (newGender != null) {
            if (!newGender.equals(teacher.getGender())) {
                updatedTeacher.setGender(newGender);
                hasAnyChange = true;
                System.out.println("Gender Changed to: " + newGender);
            }
        }

// Contact
        if (!newContact.isEmpty()) {
            if (!newContact.matches("^\\d{7,15}$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Contact must be numeric (7–15 digits)");
                hasError = true;
            } else if (!newContact.equals(teacher.getContact())) {
                updatedTeacher.setContact(newContact);
                hasAnyChange = true;
                System.out.println("Contact Changed");
            }
        }

// Department
        if (selectedDepartmentId != -1) {
            if (selectedDepartmentId != teacher.getDepartmentId()) {
                int result = JOptionPane.showConfirmDialog(
                        adminDashboard,
                        "Are you sure you want to change the department?",
                        "Confirm Department Change",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    updatedTeacher.setDepartmentId(selectedDepartmentId);
                    hasAnyChange = true;
                } else {
                    hasError = true;
                    management.getTeacherDepartment().setSelectedIndex(0);
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

        return updatedTeacher;

    }

    private void adminUpdateController() {
        management.addLoadAdminDataListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String adminId = management.getAdminIdTextField().getText().trim();
                if (adminId.matches("^\\d+$")) {
                    model.Admin admin = validateAdminId(Integer.parseInt(adminId));
                    loadAdminData(admin);
                } else {
                    JOptionPane.showMessageDialog(adminDashboard, "Enter integer value only");
                }
            }
        });
    }


    private model.Admin validateAdminId(int adminId) {
        User user = new User();
        ;
        Integer userId = userDao.getUserId("GetUserIdByAdminId", adminId);
        model.Admin admin = new model.Admin();
        if (userId != null) {
            user.setUserId(userId);
            user.setRole("Admin");
            System.out.println("from validateAdminId before fetching: "+ user.getUserId());
            admin = (model.Admin) userDao.fetchUserData(user);
            System.out.println("from validateAdminId after fetching: "+ admin.getUserId());
        } else {

            JOptionPane.showMessageDialog(adminDashboard, "No Teacher with such id found");
            return null;
        }

        return admin;
    }


    private void loadAdminData(model.Admin admin)
    {
        JTextField firstNameField = management.getAdminFirstNameText();
        JTextField lastNameField  = management.getAdminLastNameText();
        JTextField emailField     = management.getAdminEmailText();
        JTextField addressField   = management.getAdminAddressText();
        JTextField contactField   = management.getAdminContactText();
        JComboBox<String> genderComboBox= management.getAdminGenderSelect();
        genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter AdminId to select gender"}));
        JButton updateBtn= management.getAdminUpdateBtn();

// Remove previous listeners (VERY IMPORTANT)
        for (ActionListener al : updateBtn.getActionListeners()) {
            updateBtn.removeActionListener(al);
        }

        if (admin != null) {
            firstNameField.setText(admin.getFirstName());
            lastNameField.setText(admin.getLastName());
            emailField.setText(admin.getEmail());
            addressField.setText(admin.getAddress());
            contactField.setText(admin.getContact());
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Male","Female","Other"}));
            genderComboBox.setSelectedItem(admin.getGender());
        } else {
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            contactField.setText("");
        }

// Add update listener
        management.addAdminUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.Admin updatedAdmin = updateAdmin(admin);
                if (updatedAdmin != null) {
                    if (userDao.updateAdmin(updatedAdmin)) {
                        JOptionPane.showMessageDialog(
                                adminDashboard,
                                "Admin Data Updated Successfully"
                        );
                        loadAdminData(updatedAdmin);
                    }
                }
            }
        });

    }

    public model.Admin updateAdmin(model.Admin admin) {

        if (admin == null) {
            return null;
        }

        model.Admin updatedAdmin = new model.Admin();
        updatedAdmin.setAdminId(admin.getAdminId());
        updatedAdmin.setFirstName(admin.getFirstName());
        updatedAdmin.setLastName(admin.getLastName());
        updatedAdmin.setEmail(admin.getEmail());
        updatedAdmin.setAddress(admin.getAddress());
        updatedAdmin.setContact(admin.getContact());
        updatedAdmin.setGender(admin.getGender());

        String newFirstName = management.getAdminFirstNameText().getText().trim();
        String newLastName  = management.getAdminLastNameText().getText().trim();
        String newEmail     = management.getAdminEmailText().getText().trim();
        String newAddress   = management.getAdminAddressText().getText().trim();
        String newContact   = management.getAdminContactText().getText().trim();
        String newGender    = (String) management.getAdminGenderSelect().getSelectedItem();

        boolean hasAnyChange = false;
        boolean hasError = false;

        // First Name
        if (!newFirstName.isEmpty()) {
            if (!newFirstName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "First name cannot contain numbers");
                hasError = true;
            } else if (!newFirstName.equals(admin.getFirstName())) {
                updatedAdmin.setFirstName(newFirstName);
                hasAnyChange = true;
                System.out.println("changed to"+newFirstName);
            }
        }

        // Last Name
        if (!newLastName.isEmpty()) {
            if (!newLastName.matches("^[^0-9]+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Last name cannot contain numbers");
                hasError = true;
            } else if (!newLastName.equals(admin.getLastName())) {
                updatedAdmin.setLastName(newLastName);
                hasAnyChange = true;
                System.out.println("changed to"+newLastName);
            }
        }

        // Email
        if (!newEmail.isEmpty()) {
            if (!newEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Invalid email format");
                hasError = true;
            } else if (!newEmail.equals(admin.getEmail())) {
                updatedAdmin.setEmail(newEmail);
                hasAnyChange = true;
                System.out.println("changed to"+newEmail);
            }
        }

        // Address
        if (!newAddress.isEmpty()) {
            if (!newAddress.equals(admin.getAddress())) {
                updatedAdmin.setAddress(newAddress);
                hasAnyChange = true;
                System.out.println("changed to"+newAddress);
            }
        }

        // Gender
        if (newGender != null && !newGender.equals(admin.getGender())) {
            updatedAdmin.setGender(newGender);
            hasAnyChange = true;
            System.out.println("changed to"+newGender);
        }

        // Contact
        if (!newContact.isEmpty()) {
            if (!newContact.matches("^\\d{7,15}$")) {
                JOptionPane.showMessageDialog(adminDashboard,
                        "Contact must be numeric (7–15 digits)");
                hasError = true;
            } else if (!newContact.equals(admin.getContact())) {
                updatedAdmin.setContact(newContact);
                hasAnyChange = true;
                System.out.println("changed to"+newContact);
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

        return updatedAdmin;
    }



    private void courseManagementController()
    {
        addCourseController();
        updateCourseController();

        JComboBox<String> courseSelectComboBox= management.getCourseSelect();
        management.addCourseSelectActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(courseSelectComboBox.getSelectedIndex()==0)
                management.getCourseContentPanelLayout().show(management.getCourseContentPanel(),"addCourse");
                else
                    management.getCourseContentPanelLayout().show(management.getCourseContentPanel(),"updateCourse");
            }
        });
    }


    public void addCourseController()
    {
        management.addCourseAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    model.Course course= validateCourseFields();
                    if(course!=null)
                       if(courseDao.addCourse(course))
                       {
                           JOptionPane.showMessageDialog(adminDashboard,"New Course Added");
                       }


            }
        });

    }

    public model.Course validateCourseFields() {
        String departmentIdStr = management.getDepartmentIdTextField().getText().trim();
        String courseName = management.getCourseNameTextField().getText().trim();
        String durationStr = management.getCourseDurationTextField().getText().trim();

        int departmentId = -1;
        int duration = -1;

        // Validate departmentId
        if (departmentIdStr.isEmpty() || !departmentIdStr.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(adminDashboard, "Department ID must be an integer");
            return null;
        } else {
            departmentId = Integer.parseInt(departmentIdStr);
        }

        // Validate courseName
        if (courseName.isEmpty()) {
            JOptionPane.showMessageDialog(adminDashboard, "Course name cannot be empty");
            return null;
        }

        // Validate duration
        if (durationStr.isEmpty() || !durationStr.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(adminDashboard, "Course duration must be an integer");
            return null;
        } else {
            duration = Integer.parseInt(durationStr);
        }

        // If all validations pass, create Course object
        model.Course course = new model.Course();
        course.setDepartmentId(departmentId);
        course.setCourseName(courseName);
        course.setDuration(duration);

        return course;
    }


    private void updateCourseController()
    {
        management.addCourseLoadBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String courseId = management.getUpdateCourseIdTextField().getText().trim();
                if (courseId.matches("^\\d+$")) {
                    loadCourse(Integer.parseInt(courseId));
                } else {
                    JOptionPane.showMessageDialog(adminDashboard, "Enter integer value only");
                }
            }
        });



    }
    private void loadCourse(int courseId)
    {
        JTextField courseName= management.getUpdateCourseNameTextField();
        JTextField courseDuration = management.getUpdateCourseDurationTextField();
        model.Course course= courseDao.getCourseById(courseId);

        JButton updateCourseBtn= management.getCourseUpdateBtn();

        for (ActionListener al: updateCourseBtn.getActionListeners()){
            updateCourseBtn.removeActionListener(al);
        }
        if(course!=null){
        courseName.setText(course.getCourseName());
        courseDuration.setText(String.valueOf(course.getDuration()));

        management.addCourseUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.Course updatedCourse=updatedCourse(course);
                if(updatedCourse!=null)
                    if(courseDao.updateCourse(updatedCourse))
                    {
                        JOptionPane.showMessageDialog(
                                adminDashboard,
                                "Course Data Updated Successfully"
                        );
                        loadCourse(updatedCourse.getCourseId());
                    }
            }
        });
        }
        else
        {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "No course module found"
            );
        }

        management.addCourseDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(adminDashboard,"Confirm deleting this course.")==JOptionPane.YES_OPTION)
                courseDao.deleteCourse(course.getCourseId());
            }
        });
    }



    private model.Course updatedCourse(model.Course course) {

        if (course == null) {
            return null;
        }

        model.Course updatedCourse = new model.Course();
        updatedCourse.setCourseId(course.getCourseId());
        updatedCourse.setCourseName(course.getCourseName());
        updatedCourse.setDuration(course.getDuration());


        String newCourseName = management.getUpdateCourseNameTextField().getText().trim();
        String newCourseDuration = management.getUpdateCourseDurationTextField().getText().trim();

        boolean hasAnyChange = false;
        boolean hasError = false;

        // Course Name
        if (!newCourseName.isEmpty()) {
            if (!newCourseName.equals(course.getCourseName())) {
                updatedCourse.setCourseName(newCourseName);
                hasAnyChange = true;
            }
        }

        // Course Duration (INT only)
        if (!newCourseDuration.isEmpty()) {
            if (!newCourseDuration.matches("^\\d+$")) {
                JOptionPane.showMessageDialog(
                        adminDashboard,
                        "Course duration must be an integer value"
                );
                hasError = true;
            } else {
                int duration = Integer.parseInt(newCourseDuration);
                if (duration != course.getDuration()) {
                    updatedCourse.setDuration(duration);
                    hasAnyChange = true;
                }
            }
        }

        if (hasError) {
            return null;
        }

        if (!hasAnyChange) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "No changes detected to update"
            );
            return null;
        }

        return updatedCourse;
    }

    private void  batchController()
    {
        addBatchController();
        updateBatchController();
       JComboBox<String> batchSelectComboBox= management.getBatchSelect();

       management.addBatchSelectListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(batchSelectComboBox.getSelectedIndex()==0)
                   management.getBatchLayout().show(management.getBatchContentPanel(),"addBatch");
               else
                   management.getBatchLayout().show(management.getBatchContentPanel(),"updateBatch");
           }
       });
    }


    private void addBatchController()
    {
        management.addBatchAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.Batch batch= validateBatchFields();
                if(batch!=null)
                    if(batchDao.addBatch(batch))
                    {
                        JOptionPane.showMessageDialog(adminDashboard,"New Batch Added");
                    }

            }
        });
    }

    private model.Batch validateBatchFields() {
        String courseIdStr = management.getBatchCourseIdTextField().getText().trim();
        String batchName = management.getAddBatchNameTextField().getText().trim();
        String batchSection = management.getAddBatchSectionTextField().getText().trim();

        // Validate courseId is an integer
        int courseId = -1;
        if (courseIdStr.isEmpty() || !courseIdStr.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(adminDashboard, "Course ID must be an integer");
            return null;
        } else {
            courseId = Integer.parseInt(courseIdStr);
        }


        if (!batchName.isEmpty() && batchName.matches("^[0-9].*")) {
            JOptionPane.showMessageDialog(adminDashboard, "Batch name cannot start with a number");
            return null;
        }

        // Validate batchSection (single alphabet character)
        if (batchSection.isEmpty() || !batchSection.matches("^[A-Za-z]$")) {
            JOptionPane.showMessageDialog(adminDashboard, "Batch section must be a single alphabet character");
            return null;
        }

        // Create and return Batch object
        model.Batch batch = new model.Batch();
        batch.setCourseId(courseId);
        batch.setBatchName(batchName);
        batch.setSection(batchSection.charAt(0));
        batch.setSemester(1);
        batch.setCourseYear(1);

        return batch;
    }


    private void updateBatchController(){}

}

