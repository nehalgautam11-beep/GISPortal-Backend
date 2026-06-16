package com.gis.backend.controller;

import com.gis.backend.entity.Attendance;
import com.gis.backend.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(
            AttendanceService service
    ) {

        this.service = service;
    }



    @GetMapping("/class")
    public List<Attendance> getClassAttendance(

            @RequestParam
            String className
    ) {

        return service.getClassAttendance(
                className
        );
    }


    @PostMapping
    public Attendance saveAttendance(
            @RequestBody Attendance attendance
    ) {

        return service.saveAttendance(
                attendance
        );
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> saveAttendanceBulk(

            @RequestBody
            List<Attendance> attendanceList
    ) {

        try {

            List<Attendance> result =
                    service.saveAttendanceBulk(
                            attendanceList
                    );

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }


    @GetMapping("/student")
    public List<Attendance> getStudentAttendance(
            @RequestParam Long studentId
    ) {

        return service.getStudentAttendance(
                studentId
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editAttendance(
            @RequestParam Long id,
            @RequestParam boolean present
    ) {

        try {

            Attendance result =
                    service.editAttendance(
                            id,
                            present
                    );

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/class-history")
    public List<Attendance> getClassHistory(

            @RequestParam
            String className,

            @RequestParam
            String start,

            @RequestParam
            String end
    ) {

        return service.getClassHistory(
                className,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @GetMapping("/student-history")
    public List<Attendance> getStudentHistory(

            @RequestParam
            Long studentId,

            @RequestParam
            String start,

            @RequestParam
            String end
    ) {

        return service.getStudentHistory(
                studentId,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @GetMapping("/summary")
    public Map<String, Object> getAttendanceSummary(

            @RequestParam
            String className,

            @RequestParam
            String start,

            @RequestParam
            String end
    ) {

        return service.getAttendanceSummary(
                className,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @GetMapping("/defaulters")
    public List<String> getDefaulters(

            @RequestParam
            String className,

            @RequestParam
            String start,

            @RequestParam
            String end
    ) {

        return service.getDefaulters(
                className,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }
}