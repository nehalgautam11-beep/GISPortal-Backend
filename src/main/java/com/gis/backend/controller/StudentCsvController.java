package com.gis.backend.controller;

import com.gis.backend.service.StudentCsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentCsvController {

    private final StudentCsvService service;

    public StudentCsvController(
            StudentCsvService service
    ) {

        this.service = service;
    }

    @PostMapping("/upload-csv")
    public Map<String, String> uploadCsv(
            @RequestParam("file")
            MultipartFile file
    ) {

        System.out.println("CSV CONTROLLER HIT NEW VERSION");

        int savedCount = service.uploadCsv(file);

        return Map.of(
                "message",
                savedCount + " students imported successfully"
        );
    }





}