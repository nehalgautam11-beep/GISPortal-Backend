package com.gis.backend.controller;

import com.gis.backend.entity.Assignment;
import com.gis.backend.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(
            AssignmentService service
    ) {
        this.service = service;
    }

    // Create homework
    @PostMapping
    public Assignment uploadAssignment(
            @RequestBody Assignment assignment
    ) {
        return service.uploadAssignment(assignment);
    }

    // Admin: all homework
    @GetMapping
    public List<Assignment> getAssignments() {
        return service.getAssignments();
    }

    // Teacher: see only their assigned homework
    @GetMapping("/teacher")
    public List<Assignment> getByTeacher(
            @RequestParam String teacherName
    ) {
        return service.getByTeacher(teacherName);
    }

    // Student: see homework for their class
    @GetMapping("/class")
    public List<Assignment> getByClass(
            @RequestParam String className
    ) {
        return service.getByClass(className);
    }

    // Delete homework by id
    @DeleteMapping("/{id}")
    public void deleteAssignment(
            @PathVariable Long id
    ) {
        service.deleteAssignment(id);
    }
}