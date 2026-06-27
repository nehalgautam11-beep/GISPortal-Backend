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

    // Create homework
    public Assignment uploadAssignment(
            Assignment assignment
    ) {
        assignment.setUploadDate(LocalDateTime.now());
        return repository.save(assignment);
    }

    // Admin: all homework
    public List<Assignment> getAssignments() {
        return repository.findAll();
    }

    // Teacher: only their own assigned homework
    public List<Assignment> getByTeacher(String teacherName) {
        return repository.findByTeacherName(teacherName);
    }

    // Student: homework for their class
    public List<Assignment> getByClass(String className) {
        return repository.findByClassName(className);
    }

    // Delete (teacher deletes own; admin deletes any)
    public void deleteAssignment(Long id) {
        repository.deleteById(id);
    }
}