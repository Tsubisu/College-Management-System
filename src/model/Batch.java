package model;

public class Batch {
    private int batchId;
    private String batchName;
    private char section;
    private int courseId;
    private int courseYear;
    private int semester;
    private String courseName;


    public Batch(){}
    public String toString()
    {
        return (batchName+"  "+section);
    }
    public Batch(int id, String batchName,char section)
    {
        this.batchId=id;
        this.batchName=batchName;
        this.section=section;
    }


    public Batch(int batchId, String batchName,char section, int courseYear, int semester)
    {
        this.batchId=batchId;
        this.batchName=batchName;
        this.section=section;
        this.courseYear=courseYear;
        this.semester=semester;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


}
