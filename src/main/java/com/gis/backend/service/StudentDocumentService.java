package com.gis.backend.service;

import com.gis.backend.entity.StudentDocument;
import com.gis.backend.repository.StudentDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDocumentService {

    private final StudentDocumentRepository repository;

    public StudentDocumentService(
            StudentDocumentRepository repository
    ) {

        this.repository = repository;
    }

    public StudentDocument saveDocument(
            StudentDocument document
    ) {

        return repository.save(
                document
        );
    }

    public List<StudentDocument> getDocuments(
            Long studentId
    ) {

        return repository.findByStudentId(
                studentId
        );
    }

    public void deleteDocument(
            Long id
    ) {

        repository.deleteById(
                id
        );
    }
}