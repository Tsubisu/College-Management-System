package model;

import java.time.LocalDate;

public class Notice {
    private int notificationId;
    private String title;
    private String message;
    private LocalDate postedAt;
    private String targetRole;

    // No-args constructor
    public Notice() {
    }

    // Constructor without ID (for inserts)
    public Notice(String title, String message, String targetRole) {
        this.title = title;
        this.message = message;
        this.targetRole = targetRole;
    }

    // Full constructor
    public Notice(int notificationId, String title, String message,
                  LocalDate postedAt, String targetRole) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.postedAt = postedAt;
        this.targetRole = targetRole;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }
}
