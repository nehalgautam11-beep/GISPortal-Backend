package com.gis.backend.repository;

import com.gis.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    // All records for one student
    List<Attendance> findByStudentId(
            Long studentId
    );

    // All records for one class
    List<Attendance> findByClassName(
            String className
    );

    // Date-range: any class
    List<Attendance> findByAttendanceDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Date-range: specific class
    // Used by: getClassHistory, getAttendanceSummary,
    //          getDefaulters, saveAttendanceBulk (today check)
    List<Attendance> findByClassNameAndAttendanceDateBetween(
            String className,
            LocalDateTime start,
            LocalDateTime end
    );

    // Date-range: specific student
    // Used by: getStudentHistory
    List<Attendance> findByStudentIdAndAttendanceDateBetween(
            Long studentId,
            LocalDateTime start,
            LocalDateTime end
    );
}