package com.gis.backend.controller;

import com.gis.backend.entity.Timetable;
import com.gis.backend.service.TimetableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetables")
public class TimetableController {

    private final TimetableService service;

    public TimetableController(
            TimetableService service
    ) {

        this.service = service;
    }

    @PostMapping
    public Timetable saveTimetable(
            @RequestBody Timetable timetable
    ) {

        return service.saveTimetable(
                timetable
        );
    }

    @GetMapping
    public List<Timetable> getAllTimetables() {

        return service.getAllTimetables();
    }

    @GetMapping("/class")
    public List<Timetable> getClassTimetable(
            @RequestParam String className
    ) {

        return service.getTimetablesForClass(
                className
        );
    }

    @GetMapping("/teacher")
    public List<Timetable> getTeacherTimetable(
            @RequestParam String teacherName,
            @RequestParam(
                    required = false,
                    defaultValue = ""
            ) String assignedClasses
    ) {

        return service.getTimetablesForTeacher(
                teacherName,
                assignedClasses
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTimetable(
            @PathVariable Long id
    ) {

        service.deleteTimetable(id);
    }
}