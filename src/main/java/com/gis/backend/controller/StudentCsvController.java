package com.gis.backend.controller;

import com.gis.backend.service.StudentCsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String uploadCsv(

            @RequestParam("file")
            MultipartFile file
    ) {

        int savedCount =
                service.uploadCsv(
                        file
                );

        return savedCount
                + " students imported successfully";
    }
}