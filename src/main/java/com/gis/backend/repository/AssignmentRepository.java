package com.gis.backend.repository;

import com.gis.backend.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {
}