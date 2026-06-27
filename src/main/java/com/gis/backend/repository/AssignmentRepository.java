package com.gis.backend.repository;

import com.gis.backend.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    // Student: see homework for their class
    List<Assignment> findByClassName(String className);

    // Teacher: see homework they assigned
    List<Assignment> findByTeacherName(String teacherName);
}