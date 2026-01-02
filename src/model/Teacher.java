package model;

import java.util.ArrayList;

public class Teacher extends User{
    private String departmentName;
    private int departmentId;
    private int teacherId;
    private ArrayList<model.Module> teacherModules;
    private ArrayList<model.Module> newTeacherModulesToAdd;
    private ArrayList<model.Module> TeacherModulesToDelete;
    private String routinePdfPath;


    public Teacher(int userId, int teacherId,String firstName, String lastName, String email, String password, String address, String contact, String gender,int departmentId, String departmentName) {
        super(userId, firstName, lastName, email, password, address, contact, gender, "Teacher");
        this.teacherId=teacherId;
        this.departmentId=departmentId;
        this.departmentName=departmentName;
    }

    public Teacher(int teacherId,String firstName, String lastName,String departmentName) {

        this.teacherId=teacherId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.departmentName=departmentName;
    }

    public String toString()
    {
        return firstName + " "+lastName +"("+teacherId+")";
    }

    public Teacher(){}

    public void setDepartment(String departmentName)
    {
        this.departmentName= departmentName;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public ArrayList<Module> getTeacherModules() {
        return teacherModules;
    }

    public void setTeacherModules(ArrayList<Module> teacherModules) {
        this.teacherModules = teacherModules;
    }

    public ArrayList<Module> getNewTeacherModulesToAdd() {
        return newTeacherModulesToAdd;
    }

    public void setNewTeacherModulesToAdd(ArrayList<Module> newTeacherModulesToAdd) {
        this.newTeacherModulesToAdd = newTeacherModulesToAdd;
    }

    public ArrayList<Module> getTeacherModulesToDelete() {
        return TeacherModulesToDelete;
    }

    public void setTeacherModulesToDelete(ArrayList<Module> teacherModulesToDelete) {
        this.TeacherModulesToDelete = teacherModulesToDelete;
    }

    public String getRoutinePdfPath() {
        return routinePdfPath;
    }

    public void setRoutinePdfPath(String routinePdfPath) {
        this.routinePdfPath = routinePdfPath;
    }
}
