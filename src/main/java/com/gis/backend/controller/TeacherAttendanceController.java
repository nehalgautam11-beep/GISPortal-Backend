package com.gis.backend.controller;

import com.gis.backend.entity.TeacherAttendance;
import com.gis.backend.service.TeacherAttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher-attendance")
public class TeacherAttendanceController {

    private final TeacherAttendanceService service;

    public TeacherAttendanceController(
            TeacherAttendanceService service
    ) {
        this.service = service;
    }

    // Admin marks attendance for all teachers at once
    @PostMapping("/bulk")
    public ResponseEntity<?> saveBulk(
            @RequestBody List<TeacherAttendance> list
    ) {
        try {
            return ResponseEntity.ok(
                    service.saveTeacherAttendanceBulk(list)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // Admin edits a record (within 24h)
    @PutMapping("/edit")
    public ResponseEntity<?> edit(
            @RequestParam Long id,
            @RequestParam boolean present
    ) {
        try {
            return ResponseEntity.ok(
                    service.editAttendance(id, present)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // Teacher views their own attendance
    @GetMapping("/teacher")
    public List<TeacherAttendance> getTeacherAttendance(
            @RequestParam Long teacherId
    ) {
        return service.getTeacherAttendance(teacherId);
    }

    // Admin views all teacher attendance for date range
    @GetMapping("/history")
    public List<TeacherAttendance> getHistory(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return service.getAttendanceBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    // Summary report for admin
    @GetMapping("/summary")
    public Map<String, Object> getSummary(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return service.getSummary(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }
}