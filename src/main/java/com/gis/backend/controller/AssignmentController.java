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

    @PostMapping
    public Assignment uploadAssignment(
            @RequestBody Assignment assignment
    ) {

        return service.uploadAssignment(
                assignment
        );
    }

    @GetMapping
    public List<Assignment> getAssignments() {

        return service.getAssignments();
    }
}