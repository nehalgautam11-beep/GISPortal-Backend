package com.gis.backend.service;

import com.gis.backend.entity.TeacherAttendance;
import com.gis.backend.repository.TeacherAttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherAttendanceService {

    private final TeacherAttendanceRepository repository;

    public TeacherAttendanceService(
            TeacherAttendanceRepository repository
    ) {
        this.repository = repository;
    }

    // Bulk save for all teachers in one shot (daily)
    public List<TeacherAttendance> saveTeacherAttendanceBulk(
            List<TeacherAttendance> list
    ) {
        if (list.isEmpty()) {
            throw new RuntimeException("Attendance list is empty");
        }

        LocalDateTime startOfDay =
                LocalDateTime.now().toLocalDate().atStartOfDay();

        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // Check if already submitted today for any of these teachers
        List<Long> teacherIds = list.stream()
                .map(TeacherAttendance::getTeacherId)
                .collect(Collectors.toList());

        List<TeacherAttendance> existing =
                repository.findByAttendanceDateBetweenAndTeacherIdIn(
                        startOfDay, endOfDay, teacherIds
                );

        if (!existing.isEmpty()) {
            throw new RuntimeException(
                    "Teacher attendance already submitted for today"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        for (TeacherAttendance ta : list) {
            ta.setAttendanceDate(now);
            ta.setFinalized(true);
            ta.setFinalizedAt(now);
        }

        return repository.saveAll(list);
    }

    // All attendance for one teacher
    public List<TeacherAttendance> getTeacherAttendance(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    // All teachers' attendance for a date range
    public List<TeacherAttendance> getAttendanceBetween(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return repository.findByAttendanceDateBetween(start, end);
    }

    // Summary for date range
    public Map<String, Object> getSummary(
            LocalDateTime start,
            LocalDateTime end
    ) {
        List<TeacherAttendance> records =
                repository.findByAttendanceDateBetween(start, end);

        long presentCount = records.stream()
                .filter(TeacherAttendance::isPresent)
                .count();

        long absentCount = records.size() - presentCount;

        double percentage = records.isEmpty()
                ? 0.0
                : (presentCount * 100.0) / records.size();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRecords", records.size());
        summary.put("presentCount", presentCount);
        summary.put("absentCount", absentCount);
        summary.put("attendancePercentage", percentage);

        return summary;
    }

    // Edit within 24h
    public TeacherAttendance editAttendance(Long id, boolean present) {

        TeacherAttendance ta = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Record not found")
                );

        if (ta.isFinalized() && ta.getFinalizedAt() != null) {

            LocalDateTime lockTime = ta.getFinalizedAt().plusHours(24);

            if (LocalDateTime.now().isAfter(lockTime)) {
                throw new RuntimeException(
                        "Teacher attendance locked after 24 hours"
                );
            }
        }

        ta.setPresent(present);
        return repository.save(ta);
    }
}