package model;
public class Teacher extends User{
    private String departmentName;
    private int departmentId;
    private int teacherId;

    public Teacher(int userId, int teacherId,String firstName, String lastName, String email, String password, String address, String contact, String gender,int departmentId, String departmentName) {
        super(userId, firstName, lastName, email, password, address, contact, gender, "Teacher");
        this.teacherId=teacherId;
        this.departmentId=departmentId;
        this.departmentName=departmentName;
    }

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
}
