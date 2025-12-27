package model;

public class Chapter {

    private int chapterId;
    private int moduleId;
    private String chapterName;
    private String pdfPath;

    // 🔹 No-arg constructor
    public Chapter() {
    }

    // 🔹 Parameterized constructor
    public Chapter(int chapterId, int moduleId, String chapterName, String pdfPath) {
        this.chapterId = chapterId;
        this.moduleId = moduleId;
        this.chapterName = chapterName;
        this.pdfPath = pdfPath;
    }

    public Chapter(int chapterId, String chapterName, String pdfPath) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.pdfPath = pdfPath;
    }

    // 🔹 Getters & Setters

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}

