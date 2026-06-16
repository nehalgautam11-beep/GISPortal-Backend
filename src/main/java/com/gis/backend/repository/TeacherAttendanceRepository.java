package com.gis.backend.repository;

import com.gis.backend.entity.TeacherAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherAttendanceRepository
        extends JpaRepository<TeacherAttendance, Long> {

    List<TeacherAttendance> findByTeacherId(Long teacherId);

    List<TeacherAttendance> findByAttendanceDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // For duplicate-today check
    List<TeacherAttendance> findByAttendanceDateBetweenAndTeacherIdIn(
            LocalDateTime start,
            LocalDateTime end,
            List<Long> teacherIds
    );

    // All records for a date range
    List<TeacherAttendance> findByTeacherIdAndAttendanceDateBetween(
            Long teacherId,
            LocalDateTime start,
            LocalDateTime end
    );
}