package model;

public class Attendance {
    private int totalPresent;
    private int totalAbsent;
    private int totalDays;

    public Attendance(int totalPresent, int totalAbsent, int totalDays) {
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
        this.totalDays = totalDays;
    }


    public int getTotalPresent() { return totalPresent; }
    public int getTotalAbsent() { return totalAbsent; }
    public int getTotalDays() { return totalDays; }
}

