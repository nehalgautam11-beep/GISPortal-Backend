package com.gis.backend.service;

import com.gis.backend.entity.Assignment;
import com.gis.backend.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository repository;

    public AssignmentService(
            AssignmentRepository repository
    ) {

        this.repository = repository;
    }

    public Assignment uploadAssignment(
            Assignment assignment
    ) {

        assignment.setUploadDate(
                LocalDateTime.now()
        );

        return repository.save(
                assignment
        );
    }

    public List<Assignment> getAssignments() {

        return repository.findAll();
    }
}