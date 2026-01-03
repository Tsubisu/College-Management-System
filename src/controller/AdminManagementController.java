package controller;

import dao.BatchDao;
import dao.Department;
import dao.Enroll;
import dao.UserData;
import model.Chapter;
import model.Course;
import model.Student;
import model.User;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdminManagementController {
    private final AdminDashboard adminDashboard;
    private final Management management;
    private final dao.UserData userDao = new UserData();
    private final dao.Course courseDao = new dao.Course();
    private final dao.Enroll enrollDao = new Enroll();
    private final dao.Department departmentDao = new Department();
    private final dao.BatchDao batchDao = new BatchDao();
    private final dao.Module moduleDao = new dao.Module();
    private ArrayList<Course> currentCourseList = new ArrayList<>();
    private ArrayList<Course> currentBatchCourseList = new ArrayList<>();
    private ArrayList<model.Batch> batches;


    public AdminManagementController(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
        management = ((AdminDashboardPanel) adminDashboard.getDashPanel()).getManagement();
        studentUpdateController();
        teacherUpdateController();
        adminUpdateController();
        courseManagementController();
        batchController();
        chapterController();
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
                        ArrayList<model.Batch> batches = enrollDao.getBatchByCourse(index);
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
        // Get all UI components
        JTextField firstNameField = management.getTeacherFirstNameText();
        JTextField lastNameField = management.getTeacherLastNameText();
        JTextField emailField = management.getTeacherEmailText();
        JTextField addressField = management.getTeacherAddressText();
        JTextField contactField = management.getTeacherContactText();
        JTextField teacherRoutine= management.getTeacherRoutine();
        JComboBox<String> genderCombo = management.getTeacherGender();
        view.ComboBoxMultiSelection<model.Module> teacherModules = management.getTeacherModuleComboBox();

        JComboBox<String> departmentComboBox = management.getTeacherDepartment();
        JButton updateBtn = management.getTeacherUpdateBtn();
        JButton routineBtn = management.getTeacherRoutineBtn();


        for (ActionListener al : updateBtn.getActionListeners()) updateBtn.removeActionListener(al);
        for (ActionListener al : departmentComboBox.getActionListeners()) departmentComboBox.removeActionListener(al);
        for (ActionListener al : routineBtn.getActionListeners()) routineBtn.removeActionListener(al);

        ArrayList<Integer> departmentIds = new ArrayList<>();

        if (teacher != null) {

            firstNameField.setText(teacher.getFirstName());
            lastNameField.setText(teacher.getLastName());
            emailField.setText(teacher.getEmail());
            addressField.setText(teacher.getAddress());
            contactField.setText(teacher.getContact());
            teacher.setTeacherModules(moduleDao.getAllTeacherModules(teacher.getTeacherId()));
            teacherRoutine.setText(teacher.getRoutinePdfPath());


            genderCombo.setModel(new DefaultComboBoxModel<>(new String[]{"Male", "Female", "Other"}));
            genderCombo.setSelectedItem(teacher.getGender());


            ArrayList<model.Department> departments = departmentDao.getDepartment();
            String[] departmentNames = new String[departments.size() + 1];
            departmentNames[0] = "Select a department";
            departmentIds.add(-1);
            for (int i = 0; i < departments.size(); i++) {
                departmentNames[i + 1] = departments.get(i).getDepartmentName();
                departmentIds.add(departments.get(i).getDepartmentId());
            }
            departmentComboBox.setModel(new DefaultComboBoxModel<>(departmentNames));


            management.addTeacherDepartmentActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                int selectedDeptIndex = departmentComboBox.getSelectedIndex();
                int selectedDeptId = departmentIds.get(selectedDeptIndex);

                ArrayList<model.Module> departmentModules;
                if (selectedDeptId != -1) {
                    departmentModules = moduleDao.getModulesByDepartment(selectedDeptId);
                } else {
                    departmentModules = new ArrayList<>();
                }

                teacherModules.setModel(new DefaultComboBoxModel<>(departmentModules.toArray(new model.Module[0])));


                if (teacher.getDepartmentId() == selectedDeptId) {
                    List<Object> modulesToSelect = new ArrayList<>();
                    for (model.Module comboItem : departmentModules) {
                        for (model.Module assigned :teacher.getTeacherModules() ) {
                            if (comboItem.getModuleId() == assigned.getModuleId()) {
                                modulesToSelect.add(comboItem);
                            }
                        }
                    }
                    teacherModules.setSelectedItems(modulesToSelect);
                }
                }
            });
            departmentComboBox.setSelectedIndex(0);
            for (int i = 1; i < departmentIds.size(); i++) {
                if (departmentIds.get(i) == teacher.getDepartmentId()) {
                    departmentComboBox.setSelectedIndex(i);
                    break;
                }
            }


            management.addTeacherRoutineActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","png","jpeg");
                    fileChooser.setFileFilter(filter);

                    int result = fileChooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        teacherRoutine.setText(selectedFile.getAbsolutePath().replace("\\","/"));

                    }

                }
            });
        } else {

            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            contactField.setText("");
            genderCombo.setModel(new DefaultComboBoxModel<>(new String[]{"Enter TeacherId to select Gender"}));
            departmentComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter TeacherId to select Departments"}));
            teacherModules.setModel(new DefaultComboBoxModel<>());
        }


        management.addTeacherUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update teacher object with changes from UI
                model.Teacher updatedTeacher = updateTeacher(teacher, departmentIds, teacherModules);

                if (updatedTeacher != null) {
                    // 1️⃣ Update basic teacher info (name, email, contact, department, etc.)
                    boolean infoUpdated = userDao.updateTeacher(updatedTeacher);

                    // 2️⃣ Update teacher modules in the database
                    boolean modulesAdded = true;
                    boolean modulesDeleted = true;

                    if (!updatedTeacher.getNewTeacherModulesToAdd().isEmpty()) {
                        modulesAdded = moduleDao.addNewTeacherModules(updatedTeacher);
                    }

                    if (!updatedTeacher.getTeacherModulesToDelete().isEmpty()) {
                        modulesDeleted =moduleDao.deleteTeacherModules(updatedTeacher);
                    }

                    // 3️⃣ Show success message only if everything succeeded
                    if (infoUpdated && modulesAdded && modulesDeleted) {
                        JOptionPane.showMessageDialog(adminDashboard, "Teacher Data Updated Successfully");
                        loadTeacherData(updatedTeacher); // reload updated data
                    } else {
                        JOptionPane.showMessageDialog(adminDashboard, "Some updates failed. Please check logs.");
                    }
                }
            }
        });

    }


    public model.Teacher updateTeacher(model.Teacher teacher, ArrayList<Integer> departmentList,
                                       view.ComboBoxMultiSelection<model.Module> teacherModules) {
        if (teacher == null || departmentList.isEmpty()) return null;

        model.Teacher updatedTeacher = new model.Teacher();
        updatedTeacher.setTeacherId(teacher.getTeacherId());
        updatedTeacher.setEmail(teacher.getEmail());
        updatedTeacher.setGender(teacher.getGender());
        updatedTeacher.setFirstName(teacher.getFirstName());
        updatedTeacher.setLastName(teacher.getLastName());
        updatedTeacher.setAddress(teacher.getAddress());
        updatedTeacher.setContact(teacher.getContact());
        updatedTeacher.setDepartmentId(teacher.getDepartmentId());
        updatedTeacher.setTeacherModules(new ArrayList<>(teacher.getTeacherModules()));
        updatedTeacher.setRoutinePdfPath(teacher.getRoutinePdfPath());


        String newFirstName = management.getTeacherFirstNameText().getText().trim();
        String newLastName = management.getTeacherLastNameText().getText().trim();
        String newEmail = management.getTeacherEmailText().getText().trim();
        String newAddress = management.getTeacherAddressText().getText().trim();
        String newContact = management.getTeacherContactText().getText().trim();
        String newGender = (String) management.getTeacherGender().getSelectedItem();
        int selectedDepartmentId = departmentList.get(management.getTeacherDepartment().getSelectedIndex());
        String newRoutinePath= management.getTeacherRoutine().getText().trim();

        boolean hasAnyChange = false;
        boolean hasError = false;

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


        if (!newAddress.isEmpty()) {
            if (!newAddress.equals(teacher.getAddress())) {
                updatedTeacher.setAddress(newAddress);
                hasAnyChange = true;
                System.out.println("Address Changed");
            }
        }


        if (newGender != null) {
            if (!newGender.equals(teacher.getGender())) {
                updatedTeacher.setGender(newGender);
                hasAnyChange = true;
                System.out.println("Gender Changed to: " + newGender);
            }
        }


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

        if (selectedDepartmentId != -1 && selectedDepartmentId != teacher.getDepartmentId()) {
            int result = JOptionPane.showConfirmDialog(adminDashboard,
                    "Are you sure you want to change the department?",
                    "Confirm Department Change",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                updatedTeacher.setDepartmentId(selectedDepartmentId);
                hasAnyChange = true;
            } else {
                hasError = true;
                management.getTeacherDepartment().setSelectedIndex(0);
            }
        }

        List<Object> selectedItems = teacherModules.getSelectedItems();
        List<model.Module> selectedModules = new ArrayList<>();

        for (Object obj : selectedItems) {
            System.out.println(obj.getClass().getSimpleName());
            if (obj instanceof model.Module) {
                selectedModules.add((model.Module) obj);
                System.out.println(((model.Module) obj).getModuleName() + " Selected");// cast each object individually
            }
        }
        List<model.Module> oldModules = teacher.getTeacherModules();
        List<model.Module> modulesToAdd = new ArrayList<>();
        List<model.Module> modulesToRemove = new ArrayList<>();

        for (model.Module m : selectedModules) {
            boolean found = false;
            for (model.Module old : oldModules) {
                if (m.getModuleId() == old.getModuleId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                modulesToAdd.add(m);
                System.out.println(m.getModuleName() + " to be added");
            }
        }
        updatedTeacher.setNewTeacherModulesToAdd(new ArrayList<>(modulesToAdd));



        for (model.Module m : oldModules) {
            boolean found = false;
            for (model.Module sel : selectedModules) {
                if (m.getModuleId() == sel.getModuleId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                modulesToRemove.add(m);
                System.out.println(m.getModuleName() + " to be deleted");
            }
        }
        updatedTeacher.setTeacherModulesToDelete(new ArrayList<>(modulesToRemove));

        if (!newRoutinePath.equals(teacher.getRoutinePdfPath())) {
            updatedTeacher.setRoutinePdfPath(newRoutinePath);
            hasAnyChange = true;
        }


        if (!modulesToAdd.isEmpty() || !modulesToRemove.isEmpty()) {
            updatedTeacher.setTeacherModules(new ArrayList<>(selectedModules));
            hasAnyChange = true;
        }

        if (hasError) return null;
        if (!hasAnyChange) {
            JOptionPane.showMessageDialog(adminDashboard, "No changes detected to update");
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
            System.out.println("from validateAdminId before fetching: " + user.getUserId());
            admin = (model.Admin) userDao.fetchUserData(user);
            System.out.println("from validateAdminId after fetching: " + admin.getUserId());
        } else {

            JOptionPane.showMessageDialog(adminDashboard, "No Teacher with such id found");
            return null;
        }

        return admin;
    }


    private void loadAdminData(model.Admin admin) {
        JTextField firstNameField = management.getAdminFirstNameText();
        JTextField lastNameField = management.getAdminLastNameText();
        JTextField emailField = management.getAdminEmailText();
        JTextField addressField = management.getAdminAddressText();
        JTextField contactField = management.getAdminContactText();
        JComboBox<String> genderComboBox = management.getAdminGenderSelect();
        genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Enter AdminId to select gender"}));
        JButton updateBtn = management.getAdminUpdateBtn();

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
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Male", "Female", "Other"}));
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
        String newLastName = management.getAdminLastNameText().getText().trim();
        String newEmail = management.getAdminEmailText().getText().trim();
        String newAddress = management.getAdminAddressText().getText().trim();
        String newContact = management.getAdminContactText().getText().trim();
        String newGender = (String) management.getAdminGenderSelect().getSelectedItem();

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
                System.out.println("changed to" + newFirstName);
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
                System.out.println("changed to" + newLastName);
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
                System.out.println("changed to" + newEmail);
            }
        }

        // Address
        if (!newAddress.isEmpty()) {
            if (!newAddress.equals(admin.getAddress())) {
                updatedAdmin.setAddress(newAddress);
                hasAnyChange = true;
                System.out.println("changed to" + newAddress);
            }
        }

        // Gender
        if (newGender != null && !newGender.equals(admin.getGender())) {
            updatedAdmin.setGender(newGender);
            hasAnyChange = true;
            System.out.println("changed to" + newGender);
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
                System.out.println("changed to" + newContact);
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


    private void courseManagementController() {
        JComboBox<String> departmentComboBox = management.getDepartmentListComboBox();
        JComboBox<String> courseComboBox = management.getCourseListComboBox();

        JButton courseUpdateBtn = management.getCourseUpdateBtn();
        JButton addNewCourseBtn = management.getAddNewCourseBtn();
        courseUpdateBtn.setEnabled(false);
        addNewCourseBtn.setEnabled(false);

        ArrayList<model.Department> departmentList = departmentDao.getDepartment();
        ArrayList<Integer> departmentIdList = new ArrayList<>();
        String[] departmentNameList = new String[departmentList.size()];

        for (int i = 0; i < departmentList.size(); i++) {
            departmentNameList[i] = departmentList.get(i).getDepartmentName();
            departmentIdList.add(departmentList.get(i).getDepartmentId());
        }

        departmentComboBox.setModel(new DefaultComboBoxModel<>(departmentNameList));
        ArrayList<Integer> courseIdList = new ArrayList<>();
        management.addDepartmentSelectActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseIdList.clear();
                currentCourseList = courseDao.getCourseByDepartmentId((departmentList.get(departmentComboBox.getSelectedIndex())).getDepartmentId());

                if (!currentCourseList.isEmpty()) {
                    String[] courseNameList = new String[currentCourseList.size()];
                    for (int i = 0; i < currentCourseList.size(); i++) {
                        courseNameList[i] = currentCourseList.get(i).getCourseName();
                        courseIdList.add(currentCourseList.get(i).getCourseId());
                    }
                    courseComboBox.setModel(new DefaultComboBoxModel<>(courseNameList));
                } else
                    courseComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"No Course Available For This Department"}));


            }
        });

        departmentComboBox.setSelectedIndex(0);

        if (!departmentList.isEmpty())
            addNewCourseBtn.setEnabled(true);

        JTextField courseId = management.getCourseIdTextField();
        JTextField courseName = management.getCourseNameTextField();
        JTextField courseDuration = management.getCourseDurationTextField();
        management.addCourseSelectActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!courseIdList.isEmpty()) {
                    model.Course currentCourse = courseDao.getCourseById(courseIdList.get(courseComboBox.getSelectedIndex()));
                    courseId.setEditable(true);
                    courseId.setText(String.valueOf(currentCourse.getCourseId()));
                    courseId.setEditable(false);

                    courseName.setText(currentCourse.getCourseName());
                    courseDuration.setText(String.valueOf(currentCourse.getDuration()) + " Year");
                } else {
                    courseId.setText("");
                    courseName.setText("");
                    courseDuration.setText("");
                }


                int index = courseComboBox.getSelectedIndex();

                if (index >= 0 && !courseIdList.isEmpty()) {
                    courseUpdateBtn.setEnabled(true);
                } else {
                    courseUpdateBtn.setEnabled(false);
                }
            }
        });

        management.addCourseUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        adminDashboard,
                        "Are you sure you want to update this course?",
                        "Confirm Update",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {

                    model.Course updatedCourse =
                            updatedCourse(currentCourseList.get(courseComboBox.getSelectedIndex()));

                    if (updatedCourse != null) {
                        if (courseDao.updateCourse(updatedCourse)) {

                            JOptionPane.showMessageDialog(
                                    adminDashboard,
                                    "Course has been updated successfully",
                                    "Update Successful",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            courseComboBox.setSelectedIndex(courseComboBox.getSelectedIndex());
                        }
                    }
                }
            }
        });


        management.addAddNewCourseBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewCoursePopUp newCoursePopUp = new NewCoursePopUp();
                javax.swing.JDialog dialog = new JDialog(adminDashboard, "Add New Course", true);
                dialog.setContentPane(newCoursePopUp);
                dialog.pack();
                dialog.setLocationRelativeTo(adminDashboard);

                newCoursePopUp.addCourseAddBtnActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(
                                adminDashboard,
                                "Are you sure you want to add this new course?",
                                "Confirm Add",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            model.Course newCourse = validateNewCourseFields(newCoursePopUp);
                            if (newCourse != null) {
                                newCourse.setDepartmentId(departmentIdList.get(departmentComboBox.getSelectedIndex()));
                                if (courseDao.addCourse(newCourse)) {
                                    JOptionPane.showMessageDialog(newCoursePopUp, "New Course " + newCourse.getCourseName() + " has been added successfully");
                                    dialog.dispose();
                                }
                            }
                        }
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private model.Course validateNewCourseFields(NewCoursePopUp newCoursePopUp) {
        JTextField courseNameField = newCoursePopUp.getCourseNameTextField();
        JTextField durationField = newCoursePopUp.getCourseDurationTextField();

        String courseName = courseNameField.getText().trim();
        String durationText = durationField.getText().trim();

        // Course name empty
        if (courseName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "Course name cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            courseNameField.requestFocus();
            return null;
        }

        // Course name starts with number
        if (Character.isDigit(courseName.charAt(0))) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "Course name cannot start with a number",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            courseNameField.requestFocus();
            return null;
        }

        // Duration empty
        if (durationText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "Course duration is required",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            durationField.requestFocus();
            return null;
        }

        // Duration must be integer
        int duration;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "Course duration must be a valid integer",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            durationField.requestFocus();
            return null;
        }

        // Duration must be > 0
        if (duration <= 0) {
            JOptionPane.showMessageDialog(
                    adminDashboard,
                    "Course duration must be greater than 0",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            durationField.requestFocus();
            return null;
        }

        // ✅ Build and return Course
        model.Course course = new model.Course();
        course.setCourseName(courseName);
        course.setDuration(duration);

        return course;

    }


    private model.Course updatedCourse(model.Course course) {

        if (course == null) {
            return null;
        }

        model.Course updatedCourse = new model.Course();
        updatedCourse.setCourseId(course.getCourseId());
        updatedCourse.setCourseName(course.getCourseName());
        updatedCourse.setDuration(course.getDuration());


        String newCourseName = management.getCourseNameTextField().getText().trim();
        String newCourseDuration = management.getCourseDurationTextField().getText().trim();

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

    private void batchController() {
        JComboBox<String> departmentComboBox = management.getBatchDepartmentList();
        JComboBox<String> courseComboBox = management.getBatchCourseList();
        JTable batchTable = management.getBatchTable();
        DefaultTableModel batchTableModel = (DefaultTableModel) management.getBatchTable().getModel();
        JButton addBatchBtn = management.getBatchAddBtn();
        addBatchBtn.setEnabled(false);


        ArrayList<model.Department> departmentList = departmentDao.getDepartment();
        ArrayList<Integer> departmentIdList = new ArrayList<>();
        String[] departmentNameList = new String[departmentList.size()];

        for (int i = 0; i < departmentList.size(); i++) {
            departmentNameList[i] = departmentList.get(i).getDepartmentName();
            departmentIdList.add(departmentList.get(i).getDepartmentId());
        }

        departmentComboBox.setModel(new DefaultComboBoxModel<>(departmentNameList));
        ArrayList<Integer> courseIdList = new ArrayList<>();




        management.addBatchDepartmentComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseIdList.clear();
                currentBatchCourseList = courseDao.getCourseByDepartmentId((departmentList.get(departmentComboBox.getSelectedIndex())).getDepartmentId());

                if (!currentBatchCourseList.isEmpty()) {
                    String[] courseNameList = new String[currentBatchCourseList.size()];
                    for (int i = 0; i < currentBatchCourseList.size(); i++) {
                        courseNameList[i] = currentBatchCourseList.get(i).getCourseName();
                        courseIdList.add(currentBatchCourseList.get(i).getCourseId());
                    }
                    courseComboBox.setModel(new DefaultComboBoxModel<>(courseNameList));
                } else
                    courseComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"No Course Available For This Department"}));


            }
        });

        departmentComboBox.setSelectedIndex(0);


        TableActionEvent actionEvent= new TableActionEvent() {
            @Override
            public void onUpdate(int row) {
                int batchId= (int)batchTable.getValueAt(row,0);
                model.Batch batch= batches.get(row);
                setBatchUpdatePage(batch);
                management.getAllBatchContainerLayout().show(management.getAllBatchContainer(),"batchDetailPane");

            }

            @Override
            public void onDelete(int row) {
                int batchId= (int)batchTable.getValueAt(row,0);
                String batchName=(String)batchTable.getValueAt(row,1);
                int choice = JOptionPane.showConfirmDialog(
                        null,                        // Parent component, null means center of screen
                        "Do you really want to delete the batch "+batchName, // Message
                        "Delete Batch",              // Title
                        JOptionPane.YES_NO_OPTION     // Options: Yes or No
                );

                // Check user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    if(batchDao.deleteBatchById(batchId))
                        {
                            System.out.println("Batch deleted.");
                            courseComboBox.setSelectedIndex(courseComboBox.getSelectedIndex());
                        }

                    else
                        System.out.println("Batch deletion failed.");
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.out.println("Deletion canceled.");
                } else {
                    System.out.println("Dialog closed without choice.");
                }


            }
        };

        management.setTable(actionEvent);
        management.addBatchCourseComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batchTableModel.setRowCount(0);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                batchTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                batchTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                batchTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                batchTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                batchTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);


                if (!courseIdList.isEmpty()) {
                    batches = batchDao.getBatchByCourseId(currentBatchCourseList.get(courseComboBox.getSelectedIndex()).getCourseId());
                    if (!batches.isEmpty()) {
                        for (int i = 0; i < batches.size(); i++)

                            batchTableModel.addRow(new Object[]{batches.get(i).getBatchId(),
                                    batches.get(i).getBatchName(),
                                    batches.get(i).getSection(),
                                    batches.get(i).getCourseYear(),
                                    batches.get(i).getSemester(),
                                    null}
                            );
                    }
                } else {
                    batchTableModel.setRowCount(0);
                }


                int index = courseComboBox.getSelectedIndex();

                if (index >= 0 && !courseIdList.isEmpty()) {
                    addBatchBtn.setEnabled(true);
                } else {
                    addBatchBtn.setEnabled(false);
                }
            }
        });

        management.addBatchAddBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewBatchPopUp newBatchPopUp = new NewBatchPopUp();
                javax.swing.JDialog dialog = new JDialog(adminDashboard, "Add Batch", true);
                dialog.setContentPane(newBatchPopUp);
                dialog.pack();
                dialog.setLocationRelativeTo(adminDashboard);

                newBatchPopUp.addBatchAddBtnActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        model.Batch batch = validateBatchPopUpFields(newBatchPopUp);
                        if (batch == null) return;


                        int selectedCourseIndex = courseComboBox.getSelectedIndex();
                        if (selectedCourseIndex < 0 || currentBatchCourseList.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Please select a valid course",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        int courseId = currentBatchCourseList
                                .get(selectedCourseIndex)
                                .getCourseId();

                        batch.setCourseId(courseId);
                        batch.setCourseYear(1);
                        batch.setSemester(1);


                        boolean success = batchDao.addBatch(batch);

                        if (success) {
                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Batch added successfully",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dialog.dispose();
                            courseComboBox.setSelectedIndex(courseComboBox.getSelectedIndex());
                        }
                    }
                });

                dialog.setVisible(true);


            }
        });
    }

    private model.Batch validateBatchPopUpFields(NewBatchPopUp newBatchPopUp) {

        JTextField batchNameField = newBatchPopUp.getBatchNameTextField();
        JTextField sectionField   = newBatchPopUp.getBatchSectionTextField();

        String batchName = batchNameField.getText().trim();
        String sectionText = sectionField.getText().trim();

        if(batchName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    newBatchPopUp,
                    "Batch name cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }

        // Batch name must not start with a number
        if (!batchName.matches("^[A-Za-z].*")) {
            JOptionPane.showMessageDialog(
                    newBatchPopUp,
                    "Batch name must start with a letter",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }

        // Section must be exactly one alphabet character
        if (!sectionText.matches("^[A-Za-z]$")) {
            JOptionPane.showMessageDialog(
                    newBatchPopUp,
                    "Section must be a single letter (A-Z)",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }

        model.Batch batch = new model.Batch();
        batch.setBatchName(batchName);
        batch.setSection(sectionText.charAt(0));


        return batch;
    }

    private void setBatchUpdatePage(model.Batch batch)
    {
        JTextField batchNameField = management.getNewBatchName();
        JTextField batchSectionField = management.getNewBatchSection();
        JTextField batchYearField = management.getNewBatchYear();
        JTextField batchSemesterField = management.getNewBatchSemester();
        JButton returnBtn= management.getReturnBtn();
        JComboBox<model.Module> batchModuleComboBox= management.getBatchModules();
        JComboBox<model.Teacher> batchModuleTeacherList= management.getBatchUpdateLecturerComboBox();
        JButton routineButton = management.getRoutinBtn();
        JButton updateButton = management.getBatchUpdateBtn();
        JTextField routinePath=management.getRoutineTextField();

        for(ActionListener al:batchModuleComboBox.getActionListeners())
            batchModuleComboBox.removeActionListener(al);


        for(ActionListener al:returnBtn.getActionListeners())
            returnBtn.removeActionListener(al);


        for(ActionListener al:routineButton.getActionListeners())
            routineButton.removeActionListener(al);

        for(ActionListener al:updateButton.getActionListeners())
            updateButton.removeActionListener(al);






        if(batch!=null)
        {
            batchNameField.setText(batch.getBatchName());
            batchSectionField.setText(String.valueOf(batch.getSection()));
            batchYearField.setText(String.valueOf(batch.getCourseYear()));
            batchSemesterField.setText(String.valueOf(batch.getSemester()));
            ArrayList<model.Module> batchModules= moduleDao.getBatchModules(batch);
            batchModuleComboBox.setModel(new DefaultComboBoxModel<>(batchModules.toArray(new model.Module[0])));
            routinePath.setText(batch.getRoutinePdfPath());

            management.addBatchModulesActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int selectedModuleIndex = batchModuleComboBox.getSelectedIndex();
                    int selectedModuleId = batchModules.get(selectedModuleIndex).getModuleId();

                    ArrayList<model.Teacher> allModuleTeachers= moduleDao.getTeachersByModule(selectedModuleId);

                    model.Module selectedModule = (model.Module) batchModuleComboBox.getSelectedItem();
                    batch.setCurrentModuleTeacher(batchDao.getAssignedTeacherForBatchModule(batch.getBatchId(),selectedModule.getModuleId()));

                    batchModuleTeacherList.setModel(new DefaultComboBoxModel<>(allModuleTeachers.toArray(new model.Teacher[0])));
                    if (batch.getCurrentModuleTeacher()!= null) {
                        for (int i = 0; i < batchModuleTeacherList.getItemCount(); i++) {
                            model.Teacher t = batchModuleTeacherList.getItemAt(i);

                            if (t.getTeacherId() == batch.getCurrentModuleTeacher().getTeacherId()) {
                                batchModuleTeacherList.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }

            });
        }

        management.addReturnBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                management.getAllBatchContainerLayout().show(management.getAllBatchContainer(),"tablePane");

            }
        });
        management.addRoutineBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","png","jpeg");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    routinePath.setText(selectedFile.getAbsolutePath().replace("\\","/"));

                }
            }
        });

        management.addBatchUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.Batch updatedBatch = updateBatch(batch);
                if (updatedBatch == null) {

                    return;
                }


                model.Module selectedModule = (model.Module) batchModuleComboBox.getSelectedItem();
                model.Teacher selectedTeacher = (model.Teacher) batchModuleTeacherList.getSelectedItem();


                boolean success = batchDao.updateBatch(updatedBatch, selectedModule, selectedTeacher);

                if (success) {
                    {JOptionPane.showMessageDialog(
                            management.getAllBatchContainer(),
                            "Batch updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE

                    );
                        setBatchUpdatePage(updatedBatch);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            management.getAllBatchContainer(),
                            "Failed to update batch. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

    }

    public model.Batch updateBatch(model.Batch batch) {

        if (batch == null) return null;

        model.Batch updatedBatch = new model.Batch();
        updatedBatch.setBatchId(batch.getBatchId());
        updatedBatch.setBatchName(batch.getBatchName());
        updatedBatch.setSection(batch.getSection());
        updatedBatch.setCourseYear(batch.getCourseYear());
        updatedBatch.setSemester(batch.getSemester());
        updatedBatch.setRoutinePdfPath(batch.getRoutinePdfPath());
        updatedBatch.setCurrentModuleTeacher(batch.getCurrentModuleTeacher());

        JTextField batchNameField = management.getNewBatchName();
        JTextField batchSectionField = management.getNewBatchSection();
        JTextField batchYearField = management.getNewBatchYear();
        JTextField batchSemesterField = management.getNewBatchSemester();
        JTextField routinePath = management.getRoutineTextField();
        JComboBox<model.Module> batchModuleComboBox = management.getBatchModules();
        JComboBox<model.Teacher> batchModuleTeacherList = management.getBatchUpdateLecturerComboBox();

        String newName = batchNameField.getText().trim();
        String newSection = batchSectionField.getText().trim();
        String newYearStr = batchYearField.getText().trim();
        String newSemesterStr = batchSemesterField.getText().trim();
        String newRoutinePath = routinePath.getText().trim();

        model.Module selectedModule = (model.Module) batchModuleComboBox.getSelectedItem();
        model.Teacher selectedTeacher = (model.Teacher) batchModuleTeacherList.getSelectedItem();

        boolean hasAnyChange = false;
        boolean hasError = false;

        if (!newName.isEmpty() && !newName.equals(batch.getBatchName())) {
            if (!newName.matches("^[A-Za-z].*")) {
                JOptionPane.showMessageDialog(management, "Batch name must start with a letter", "Validation Error", JOptionPane.ERROR_MESSAGE);
                hasError = true;
            } else {
                updatedBatch.setBatchName(newName);
                hasAnyChange = true;
            }
        }


        if (!newSection.isEmpty() && (newSection.charAt(0) != batch.getSection())) {
            if (!newSection.matches("^[A-Za-z]$")) {
                JOptionPane.showMessageDialog(management, "Section must be a single letter (A-Z)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                hasError = true;
            } else {
                updatedBatch.setSection(newSection.charAt(0));
                hasAnyChange = true;
            }
        }


        if (!newYearStr.isEmpty()) {
            try {
                int year = Integer.parseInt(newYearStr);
                if (year != batch.getCourseYear()) {
                    updatedBatch.setCourseYear(year);
                    hasAnyChange = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(management, "Year must be an integer", "Validation Error", JOptionPane.ERROR_MESSAGE);
                hasError = true;
            }
        }

        if (!newSemesterStr.isEmpty()) {
            try {
                int semester = Integer.parseInt(newSemesterStr);
                if (semester != batch.getSemester()) {
                    updatedBatch.setSemester(semester);
                    hasAnyChange = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(management, "Semester must be an integer", "Validation Error", JOptionPane.ERROR_MESSAGE);
                hasError = true;
            }
        }

        if (!newRoutinePath.equals(batch.getRoutinePdfPath())) {
            updatedBatch.setRoutinePdfPath(newRoutinePath);
            hasAnyChange = true;
        }


        model.Teacher currentTeacher = batch.getCurrentModuleTeacher();
        if ((currentTeacher == null && selectedTeacher != null) ||
                (currentTeacher != null && (selectedTeacher == null || currentTeacher.getTeacherId() != selectedTeacher.getTeacherId()))) {
            updatedBatch.setCurrentModuleTeacher(selectedTeacher);
            hasAnyChange = true;
        }

        if (hasError) return null;

        if (!hasAnyChange) {
            JOptionPane.showMessageDialog(management, "No changes detected to update", "Info", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return updatedBatch;
    }

    private void chapterController()
    {
        JComboBox<model.Course> courseJComboBox= management.getModuleCourseComboBox();
        JComboBox<model.Module> moduleJComboBox=management.getModuleSelectionComboBOx();
        JTable chapterTable= management.getChapterTable();
        DefaultTableModel tableModel= (DefaultTableModel) chapterTable.getModel();
        JButton addBtn= management.getChapterAddBtn();
        addBtn.setEnabled(false);


        ArrayList<model.Course> allCourses= courseDao.getAllCourse();
        if(!allCourses.isEmpty())
            courseJComboBox.setModel(new DefaultComboBoxModel<>(allCourses.toArray(new model.Course[0])));



        management.addModuleCourseComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.Course selectedCourse=(model.Course) courseJComboBox.getSelectedItem();
                ArrayList<model.Module> courseModules= moduleDao.getModulesByCourse(selectedCourse.getCourseId());
                if(!courseModules.isEmpty())
                {
                    moduleJComboBox.setModel(new DefaultComboBoxModel<>(courseModules.toArray(new model.Module[0])));
                    moduleJComboBox.setSelectedIndex(0);
                    addBtn.setEnabled(true);

                }
            }
        });

        TableActionEvent tableActionEvent = new TableActionEvent() {
            @Override
            public void onUpdate(int row) {
                int chapterId= (int)chapterTable.getValueAt(row,0);
                ChapterUpdatePopUp chapterUpdatePopUp= new ChapterUpdatePopUp();
                updateChapter(chapterUpdatePopUp,chapterId);
            }

            @Override
            public void onDelete(int row) {

                int chapterId= (int)chapterTable.getValueAt(row,0);
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this chapter?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    moduleDao.deleteChapterById(chapterId);
                }

            }
        };

        management.setChapterTable(tableActionEvent);



        management.addModuleSelectionComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);

                int moduleId=((model.Module) moduleJComboBox.getSelectedItem()).getModuleId();
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                chapterTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                chapterTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

                ArrayList<model.Chapter> chapters= moduleDao.getChaptersByModule(moduleId);

                if (!chapters.isEmpty()) {
                    for (int i = 0; i < chapters.size(); i++)

                        tableModel.addRow(new Object[]{chapters.get(i).getChapterId(),
                                chapters.get(i).getChapterName(),
                                null}
                        );
                }
            }

        });


        management.addAddChapterBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                NewChapterPopUp newChapterPopUp= new NewChapterPopUp();
                javax.swing.JDialog dialog = new JDialog(adminDashboard, "Add New Chapter", true);
                dialog.setContentPane(newChapterPopUp);
                dialog.pack();
                dialog.setLocationRelativeTo(adminDashboard);

                int moduleId=((model.Module) moduleJComboBox.getSelectedItem()).getModuleId();
                final String[] pdfPath = { null };

                newChapterPopUp.addChapterPdfActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        FileNameExtensionFilter filter =
                                new FileNameExtensionFilter("Chapter PDF", "pdf");
                        fileChooser.setFileFilter(filter);

                        int result = fileChooser.showOpenDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            pdfPath[0] = selectedFile.getAbsolutePath().replace("\\", "/");
                        }
                    }
                });

                newChapterPopUp.addAddBtnActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String chapterName = newChapterPopUp.getChapterName().getText().trim();
                        System.out.println(moduleId+" "+chapterName+" "+pdfPath[0]);

                        // Chapter name validation
                        if (chapterName.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Chapter name cannot be empty",
                                    "Validation Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (Character.isDigit(chapterName.charAt(0))) {
                            JOptionPane.showMessageDialog(null,
                                    "Chapter name cannot start with a number",
                                    "Validation Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // PDF validation
                        if (pdfPath[0] == null || pdfPath[0].isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Please select a chapter PDF",
                                    "Validation Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // ✅ Safe to proceed
                        Chapter chapter = new Chapter();
                        chapter.setModuleId(moduleId);
                        chapter.setChapterName(chapterName);
                        chapter.setPdfPath(pdfPath[0]);
                        if(moduleDao.addNewChapter(chapter))
                        {
                            JOptionPane.showMessageDialog(adminDashboard,"Successfully added new chapter");
                            dialog.dispose();
                        }
                    }
                });

                dialog.setVisible(true);
            }
        });


    }

    private void updateChapter(ChapterUpdatePopUp chapterUpdatePopUp, int chapterId) {

        JDialog dialog = new JDialog(adminDashboard, "Update chapter", true);
        dialog.setContentPane(chapterUpdatePopUp);
        dialog.pack();
        dialog.setLocationRelativeTo(adminDashboard);

        JTextField chapterNameField = chapterUpdatePopUp.getChapterName();
        JTextField chapterPdfField = chapterUpdatePopUp.getCurrentPdf();

        Chapter oldChapter = moduleDao.getChapterById(chapterId);

        chapterNameField.setText(oldChapter.getChapterName());
        chapterPdfField.setText(oldChapter.getPdfPath());

        chapterUpdatePopUp.addChapterPdfActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Chapter PDF", "pdf"));

                if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    chapterPdfField.setText(file.getAbsolutePath().replace("\\", "/"));
                }
            }
        });

        chapterUpdatePopUp.addUpdatetBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String newName = chapterNameField.getText().trim();
                String newPdf = chapterPdfField.getText().trim();

                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Chapter name cannot be empty");
                    return;
                }

                if (Character.isDigit(newName.charAt(0))) {
                    JOptionPane.showMessageDialog(dialog, "Chapter name cannot start with a number");
                    return;
                }

                boolean nameChanged = !newName.equals(oldChapter.getChapterName());
                boolean pdfChanged = !newPdf.equals(
                        oldChapter.getPdfPath() == null ? "" : oldChapter.getPdfPath()
                );

                if (!nameChanged && !pdfChanged) {
                    JOptionPane.showMessageDialog(dialog, "No changes detected");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        dialog,
                        "Are you sure you want to update this chapter?",
                        "Confirm Update",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                Chapter updatedChapter = new Chapter();
                updatedChapter.setChapterId(chapterId);
                updatedChapter.setChapterName(newName);
                updatedChapter.setPdfPath(newPdf.isEmpty() ? null : newPdf);

                boolean success = moduleDao.updateChapter(updatedChapter);

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Chapter updated successfully");
                    dialog.dispose();

                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update chapter");
                }
            }
        });

        dialog.setVisible(true);
    }





}


