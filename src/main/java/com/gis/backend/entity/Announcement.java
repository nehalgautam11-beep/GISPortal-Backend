package com.gis.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private String targetRole;

    // Comma-separated class names this announcement is visible to.
    // null/"ALL" means all classes see it.
    // e.g. "6th,7th" — only those classes see it.
    @Column(columnDefinition = "TEXT")
    private String targetClasses;

    private LocalDateTime createdAt;

    public Announcement() {
    }

    public Long getId() {
        return id;
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

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(
            String targetRole
    ) {
        this.targetRole = targetRole;
    }

    public String getTargetClasses() {
        return targetClasses;
    }

    public void setTargetClasses(
            String targetClasses
    ) {
        this.targetClasses = targetClasses;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }
}