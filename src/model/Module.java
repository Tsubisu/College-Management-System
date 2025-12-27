package model;

public class Module {
    private final int moduleId;
    private String moduleName;
    private int courseYear;
    private int courseSemester;
    private int moduleDuration;
    private final int courseId;


    public Module(int moduleId,int courseId,String moduleName,int courseYear,int courseSemester,int moduleDuration )
    {   this.moduleId=moduleId;
        this.courseId= courseId;
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











//    private String intToWord(int n) {
//        return switch (n) {
//            case 0 -> "zero";
//            case 1 -> "one";
//            case 2 -> "two";
//            case 3 -> "three";
//            case 4 -> "four";
//            case 5 -> "five";
//            case 6 -> "six";
//            case 7 -> "seven";
//            case 8 -> "eight";
//            default -> "unknown";
//        };
//    }





}
