package com.gis.backend.controller;

import com.gis.backend.entity.StudentDocument;
import com.gis.backend.service.StudentDocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class StudentDocumentController {

    private final StudentDocumentService service;

    public StudentDocumentController(
            StudentDocumentService service
    ) {

        this.service = service;
    }

    @PostMapping
    public StudentDocument saveDocument(

            @RequestBody
            StudentDocument document
    ) {

        return service.saveDocument(
                document
        );
    }

    @GetMapping("/{studentId}")
    public List<StudentDocument> getDocuments(

            @PathVariable
            Long studentId
    ) {

        return service.getDocuments(
                studentId
        );
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(

            @PathVariable
            Long id
    ) {

        service.deleteDocument(
                id
        );
    }
}