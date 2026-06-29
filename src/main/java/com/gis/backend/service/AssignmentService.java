package com.gis.backend.service;

import com.gis.backend.entity.Assignment;
import com.gis.backend.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository repository;
    private final NotificationBroadcastService notificationService;

    public AssignmentService(
            AssignmentRepository repository,
            NotificationBroadcastService notificationService
    ) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public Assignment uploadAssignment(
            Assignment assignment
    ) {

        assignment.setUploadDate(LocalDateTime.now());

        Assignment saved =
                repository.save(assignment);

        try {

            notificationService.broadcastAssignment(
                    saved
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return saved;
    }

    public List<Assignment> getAssignments() {
        return repository.findAll();
    }

    public List<Assignment> getByTeacher(String teacherName) {
        return repository.findByTeacherName(teacherName);
    }

    public List<Assignment> getByClass(String className) {
        return repository.findByClassName(className);
    }

    public void deleteAssignment(Long id) {
        repository.deleteById(id);
    }
}