package com.gis.backend.repository;

import com.gis.backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository
        extends JpaRepository<Teacher, Long> {

    List<Teacher> findByNameContainingIgnoreCase(
            String name
    );

    Optional<Teacher> findByUsername(
            String username
    );
}