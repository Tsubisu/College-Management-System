package model;
public class Student extends User{
    private int courseId;
    private String courseName;
    private int year;
    private int semester;
    private String batchName;
    private int batchId;
    private int studentId;


    public Student(int userId,int studentId, String firstName, String lastName,String email,
                   String Password, String address, String contact,String gender,
                   int batchId, String batchName,int courseId, String courseName,int year ,int semester)
    {
        super(userId,firstName,lastName,email,Password,address,contact,gender,"Student");
        this.studentId= studentId;
        this.batchId=batchId;
        this.batchName=batchName;
        this.courseId= courseId;
        this.courseName= courseName;
        this.year = year;
        this.semester=semester;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatch() {
        return batchName;
    }

    public void setBatch(String batch) {
        this.batchName = batch;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourse(String courseName) {
        this.courseName = courseName;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
