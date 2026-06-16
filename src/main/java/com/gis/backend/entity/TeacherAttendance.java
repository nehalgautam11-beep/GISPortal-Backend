package com.gis.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_attendance")
public class TeacherAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teacherId;

    private String teacherName;

    private boolean present;

    private boolean finalized;

    private LocalDateTime attendanceDate;

    private LocalDateTime finalizedAt;

    private String markedBy; // Admin username

    public TeacherAttendance() {}

    public Long getId() { return id; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }

    public boolean isFinalized() { return finalized; }
    public void setFinalized(boolean finalized) { this.finalized = finalized; }

    public LocalDateTime getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDateTime attendanceDate) { this.attendanceDate = attendanceDate; }

    public LocalDateTime getFinalizedAt() { return finalizedAt; }
    public void setFinalizedAt(LocalDateTime finalizedAt) { this.finalizedAt = finalizedAt; }

    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }
}