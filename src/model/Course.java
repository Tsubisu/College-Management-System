package model;

public class Course {
    private int courseId;
    private String courseName;
    private int departmentId;

    public Course(int courseId,String courseName,int departmentId)
    {
        this.courseId=courseId;
        this.courseName=courseName;
        this.departmentId=departmentId;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
