package com.gis.backend.repository;

import com.gis.backend.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDocumentRepository
        extends JpaRepository<StudentDocument, Long> {

    List<StudentDocument> findByStudentId(
            Long studentId
    );
}