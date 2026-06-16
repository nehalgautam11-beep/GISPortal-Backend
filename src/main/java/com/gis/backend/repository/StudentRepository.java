package com.gis.backend.repository;

import com.gis.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    List<Student> findByClassName(
            String className
    );

    List<Student> findByNameContainingIgnoreCase(
            String name
    );

    Optional<Student> findByUsername(
            String username
    );
}