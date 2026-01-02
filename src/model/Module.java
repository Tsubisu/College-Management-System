package model;

public class Module {
    private final int moduleId;
    private String moduleName;
    private int courseYear;
    private int courseSemester;
    private int moduleDuration;
    private int courseId;
    private String courseName;


    public Module(int moduleId,int courseId,String moduleName,int courseYear,int courseSemester,int moduleDuration )
    {   this.moduleId=moduleId;
        this.courseId= courseId;
        this.moduleName= moduleName;
        this.courseSemester= courseSemester;
        this.moduleDuration= moduleDuration;
        this.courseYear= courseYear;
    }
    public Module(int moduleId,int courseId,String moduleName,String courseName,int courseYear,int courseSemester,int moduleDuration )
    {   this.moduleId=moduleId;
        this.courseId= courseId;
        this.moduleName= moduleName;
        this.courseName=courseName;
        this.courseSemester= courseSemester;
        this.moduleDuration= moduleDuration;
        this.courseYear= courseYear;
    }
    public Module(int moduleId,String moduleName,int courseYear,int courseSemester,int moduleDuration )
    {   this.moduleId=moduleId;
        this.moduleName= moduleName;
        this.courseSemester= courseSemester;
        this.moduleDuration= moduleDuration;
        this.courseYear= courseYear;
    }



    public int getModuleId() {
        return moduleId;
    }


    public int getCourseId() {
        return courseId;
    }



    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }



    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }



    public int getModuleDuration() {
        return moduleDuration;
    }

    public void setModuleDuration(int moduleDuration) {
        this.moduleDuration = moduleDuration;
    }



    public int getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }

    @Override
    public String toString()
    {
        return this.moduleName;
    }

}
