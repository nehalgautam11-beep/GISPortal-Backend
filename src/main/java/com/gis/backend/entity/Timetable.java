package com.gis.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "timetables")
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    private String teacherName;

    private String pdfUrl;

    private String type;

    // Comma-separated class names this timetable is visible to.
    // "ALL" means visible to all.
    // e.g. "6th,7th,8th" — only those classes see it.
    @Column(columnDefinition = "TEXT")
    private String targetClasses;

    public Timetable() {
    }

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(
            String className
    ) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(
            String teacherName
    ) {
        this.teacherName = teacherName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(
            String pdfUrl
    ) {
        this.pdfUrl = pdfUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(
            String type
    ) {
        this.type = type;
    }

    public String getTargetClasses() {
        return targetClasses;
    }

    public void setTargetClasses(String targetClasses) {
        this.targetClasses = targetClasses;
    }
}