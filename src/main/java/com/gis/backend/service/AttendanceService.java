package com.gis.backend.service;

import com.gis.backend.entity.Attendance;
import com.gis.backend.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService(
            AttendanceRepository repository
    ) {

        this.repository = repository;
    }

    // ─── Single save ──────────────────────────────────

    public Attendance saveAttendance(
            Attendance attendance
    ) {

        attendance.setAttendanceDate(
                LocalDateTime.now()
        );

        return repository.save(attendance);
    }

    // ─── Bulk save (with duplicate-today guard) ────────

    public List<Attendance> saveAttendanceBulk(
            List<Attendance> attendanceList
    ) {

        if (attendanceList.isEmpty()) {

            throw new RuntimeException(
                    "Attendance list is empty"
            );
        }

        String className =
                attendanceList.get(0).getClassName();

        LocalDateTime startOfDay =
                LocalDateTime.now()
                        .toLocalDate()
                        .atStartOfDay();

        LocalDateTime endOfDay =
                startOfDay.plusDays(1);

        List<Attendance> existing =
                repository
                        .findByClassNameAndAttendanceDateBetween(
                                className,
                                startOfDay,
                                endOfDay
                        );

        if (!existing.isEmpty()) {

            throw new RuntimeException(
                    "Attendance already submitted for today"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        for (Attendance attendance : attendanceList) {

            attendance.setAttendanceDate(now);

            attendance.setFinalized(true);

            attendance.setFinalizedAt(now);
        }

        return repository.saveAll(attendanceList);
    }

    // ─── Read: student all-time ────────────────────────

    public List<Attendance> getStudentAttendance(
            Long studentId
    ) {

        return repository.findByStudentId(studentId);
    }

    // ─── Read: class all-time ─────────────────────────

    public List<Attendance> getClassAttendance(
            String className
    ) {

        return repository.findByClassName(className);
    }

    // ─── Read: class between dates ────────────────────

    public List<Attendance> getClassHistory(
            String className,
            LocalDateTime start,
            LocalDateTime end
    ) {

        return repository
                .findByClassNameAndAttendanceDateBetween(
                        className,
                        start,
                        end
                );
    }

    // ─── Read: student between dates ──────────────────

    public List<Attendance> getStudentHistory(
            Long studentId,
            LocalDateTime start,
            LocalDateTime end
    ) {

        return repository
                .findByStudentIdAndAttendanceDateBetween(
                        studentId,
                        start,
                        end
                );
    }

    // ─── Summary: present/absent/percentage ───────────

    public Map<String, Object> getAttendanceSummary(
            String className,
            LocalDateTime start,
            LocalDateTime end
    ) {

        List<Attendance> records =
                repository
                        .findByClassNameAndAttendanceDateBetween(
                                className,
                                start,
                                end
                        );

        long presentCount =
                records.stream()
                        .filter(Attendance::isPresent)
                        .count();

        long absentCount =
                records.size() - presentCount;

        double percentage =
                records.isEmpty()
                        ? 0.0
                        : (presentCount * 100.0)
                          / records.size();

        Map<String, Object> summary = new HashMap<>();

        summary.put("presentCount", presentCount);

        summary.put("absentCount", absentCount);

        summary.put("attendancePercentage", percentage);

        return summary;
    }

    // ─── Defaulters: students below 75% ───────────────

    public List<String> getDefaulters(
            String className,
            LocalDateTime start,
            LocalDateTime end
    ) {

        List<Attendance> records =
                repository
                        .findByClassNameAndAttendanceDateBetween(
                                className,
                                start,
                                end
                        );

        // Map: studentName → [presentCount, totalCount]
        Map<String, long[]> studentStats =
                new HashMap<>();

        for (Attendance attendance : records) {

            String name = attendance.getStudentName();

            studentStats.computeIfAbsent(
                    name,
                    k -> new long[]{0, 0}
            );

            studentStats.get(name)[1]++;

            if (attendance.isPresent()) {
                studentStats.get(name)[0]++;
            }
        }

        return studentStats.entrySet().stream()
                .filter(entry -> {

                    long present = entry.getValue()[0];
                    long total = entry.getValue()[1];

                    double pct =
                            total == 0
                                    ? 0.0
                                    : (present * 100.0) / total;

                    return pct < 75.0;
                })
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }

    // ─── Edit (24-hour lock enforced) ─────────────────

    public Attendance editAttendance(
            Long id,
            boolean present
    ) {

        Attendance attendance =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Attendance record not found"
                                )
                        );

        if (
                attendance.isFinalized()
                        && attendance.getFinalizedAt() != null
        ) {

            LocalDateTime lockTime =
                    attendance.getFinalizedAt()
                            .plusHours(24);

            if (LocalDateTime.now().isAfter(lockTime)) {

                throw new RuntimeException(
                        "Attendance locked after 24 hours"
                );
            }
        }

        attendance.setPresent(present);

        return repository.save(attendance);
    }
}