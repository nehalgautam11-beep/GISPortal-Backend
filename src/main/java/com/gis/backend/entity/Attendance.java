package com.gis.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private String studentName;

    private String className;

    private boolean present;

    private boolean finalized;

    private LocalDateTime attendanceDate;

    private LocalDateTime finalizedAt;

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(
            LocalDateTime attendanceDate
    ) {
        this.attendanceDate = attendanceDate;
    }

    public LocalDateTime getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(
            LocalDateTime finalizedAt
    ) {
        this.finalizedAt = finalizedAt;
    }
}